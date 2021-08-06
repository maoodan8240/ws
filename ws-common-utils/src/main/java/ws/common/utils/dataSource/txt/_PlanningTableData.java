package ws.common.utils.dataSource.txt;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.table.data.PlanningTableData;
import ws.common.table.data.TableData;
import ws.common.table.data.TableDataCell;
import ws.common.table.data.TableDataHeader;
import ws.common.table.data.TableDataRow;
import ws.common.utils.fileHandler.FileCharacters;
import ws.common.utils.fileHandler.FileReadCharacters;
import ws.common.utils.fileHandler.FileReader;
import ws.common.utils.fileHandler._FileReadWritArgs;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class _PlanningTableData implements PlanningTableData {
    private static final Logger LOGGER = LoggerFactory.getLogger(_PlanningTableData.class);
    private final String TABLE_FILE_PATH;
    private static final String TABLE_FILE_NAME_SUFFIX = ".tab";
    private Map<String, TableData> tableNameToTableData = new ConcurrentHashMap<>();

    public _PlanningTableData(String TABLE_FILE_PATH) {
        this.TABLE_FILE_PATH = TABLE_FILE_PATH;
    }

    @Override
    public void loadAllTablesData() throws Exception {
        File root = new File(TABLE_FILE_PATH);
        if (root.exists() && root.isDirectory()) {
            _filterTabFiles(root, file -> _readTabFile(file));
        } else {
            throw new RuntimeException("tab文件路径有错！Path=" + TABLE_FILE_PATH);
        }
    }

    @Override
    public List<String> loadChangedTablesData() throws Exception {
        File root = new File(TABLE_FILE_PATH);
        List<String> changedFileNames = new ArrayList<>();
        if (root.exists() && root.isDirectory()) {
            _filterTabFiles(root, file -> _findChangedTabFiles(file, changedFileNames));
        } else {
            throw new RuntimeException("tab文件路径有错！Path=" + TABLE_FILE_PATH);
        }
        return changedFileNames;
    }

    @Override
    public Map<String, TableData> getTableNameToTableData() {
        return tableNameToTableData;
    }

    /**
     * 过滤所有tab文件
     *
     * @param file
     */
    private void _filterTabFiles(File file, CallTabFile call) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    _filterTabFiles(f, call);
                }
            } else {
                if (file.getName().endsWith(TABLE_FILE_NAME_SUFFIX)) {
                    call.call(file);
                }
            }
        }
    }

    /**
     * 重新加载修改过的tab文件
     *
     * @param file
     * @param changedFileNames
     */
    private void _findChangedTabFiles(File file, List<String> changedFileNames) {
        String fileName = getFileSimpleName(file);
        String path = file.getPath().replaceAll(file.getName(), "");
        TableData tableData = tableNameToTableData.get(fileName);
        if (tableData == null || (tableData != null && tableData.getLastModifiedTime() != file.lastModified())) {
            LOGGER.debug("Tab文件{}发生变化了，重新加载！", file.getName());
            if (!fileName.equals("Table_SceneList")) {
                String name = fileName.replaceAll("Table_", "");
                name = name.substring(0, name.lastIndexOf("_"));
                for (Map.Entry<String, TableData> entry : tableNameToTableData.entrySet()) {
                    if (!entry.getKey().equals("Table_SceneList")) {
                        String tableName = entry.getKey().replaceAll("Table_", "");
                        tableName = tableName.substring(0, tableName.lastIndexOf("_"));
                        if (tableName.contains(name)) {
                            File tabFile = new File(path + entry.getKey() + ".tab");
                            _readTabFile(tabFile);
                            if (!changedFileNames.contains(tabFile)) {
                                changedFileNames.add(getFileSimpleName(tabFile));
                            }
                        }
                    }
                }
            } else {
                _readTabFile(file);
                if (!changedFileNames.contains(fileName)) {
                    changedFileNames.add(fileName);
                }
            }
        }
    }


    /**
     * 读取tab文件
     *
     * @param file
     */
    private void _readTabFile(File file) {
        LOGGER.debug("加载Tab文件{}！", file.getName());
        String fileName = getFileSimpleName(file);
        TableData tableData = new TableData(fileName);
        FileCharacters characters = new FileReadCharacters(file);
        FileReader.read(new _FileReadWritArgs(characters) {
            @Override
            public Object callContentRead(BufferedReader bufRead, Object... args) throws Exception {
                String line;
                int lineIdx = 0;
                List<TableDataHeader> headers = new ArrayList<>();
                List<TableDataRow> rows = new ArrayList<>();
                while ((line = bufRead.readLine()) != null) {
                    lineIdx++;
                    if (lineIdx == 1) { // 注解
                        _loadTableHeaderDataDesc(line, headers);
                    } else if (lineIdx == 2) { // 列名
                        _loadTableHeaderDataName(line, headers);
                    } else if (lineIdx == 3) { // 列的类型
                        _loadTableHeaderDataType(line, headers);
                    } else { //
                        _loadTableRowData(line, lineIdx, rows);
                    }
                }
                tableData.setHeaderDatas(headers);
                tableData.setRows(rows);
                return null;
            }
        });
        tableData.setLastModifiedTime(file.lastModified());
        tableNameToTableData.put(tableData.getTableName(), tableData);
    }


    private void _loadTableHeaderDataDesc(String line, List<TableDataHeader> tableDataTxtHeaders) {
        String[] cells = line.split("\t");
        int i = 1;
        for (String c : cells) {
            TableDataHeader header = new TableDataHeader();
            header.setDesc(c);
            header.setColumnIdx(i);
            tableDataTxtHeaders.add(header);
            i++;
        }
    }

    private void _loadTableHeaderDataName(String line, List<TableDataHeader> tableDataTxtHeaders) {
        String[] cells = line.split("\t");
        int i = 0;
        for (String c : cells) {
            if (i > tableDataTxtHeaders.size() - 1) {
                break;
            }
            TableDataHeader header = tableDataTxtHeaders.get(i);
            header.setName(c);
            i++;
        }
    }

    private void _loadTableHeaderDataType(String line, List<TableDataHeader> tableDataTxtHeaders) {
        String[] cells = line.split("\t");
        int i = 0;
        for (String c : cells) {
            if (i > tableDataTxtHeaders.size() - 1) {
                break;
            }
            TableDataHeader header = tableDataTxtHeaders.get(i);
            header.setType(c);
            i++;
        }
    }

    private void _loadTableRowData(String line, int lineIdx, List<TableDataRow> rows) {
        String lineNew = StringUtils.trimToEmpty(line);
        if (lineNew.startsWith("####")) { // 跳过注释行
            return;
        }
        String[] cells = line.split("\t");
        TableDataRow row = new TableDataRow();
        for (String c : cells) {
            TableDataCell cell = new TableDataCell();
            cell.setCell(c);
            row.getCells().add(cell);
        }
        row.setRowIdx(lineIdx);
        rows.add(row);
    }


    private interface CallTabFile {
        void call(File file);
    }

    private static String getFileSimpleName(File file) {
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
    }
}
