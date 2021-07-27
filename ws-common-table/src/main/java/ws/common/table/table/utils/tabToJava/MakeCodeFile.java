package ws.common.table.table.utils.tabToJava;


import ws.common.table.table.exception.TableHeaderParseFailedException;

import java.io.File;
import java.io.IOException;

public class MakeCodeFile {


    public static void main(String[] args) throws Exception {
        genTableRows("D:\\work_space\\drama-project\\gameServer\\src\\main\\resources\\data.tab\\Table_Stage.tab", "D:\\work_space\\drama-project\\relationship\\src\\main\\java\\", //
                "dm.relationship.table.tableRows", //
                "Table_Stage_Row");
    }

    public static void genTableRows(String tabFileName, String fullProjectSrcPath, String packagePath, String newFileName) throws IOException, TableHeaderParseFailedException {

        StringBuffer sb = GenTableRowAccesserCodeFile.printCode(tabFileName, packagePath, newFileName);

        String filePath = packagePath.replace(".", "/");

        File file = new File(fullProjectSrcPath + filePath + "/" + newFileName + ".java");
        System.out.println(file);
        if (file.exists() && file.isFile()) {
            GenTableRowAccesserCodeFile.clearFile(file);
        } else {
            file.createNewFile();
        }
        GenTableRowAccesserCodeFile.writeToFile(file, sb.toString(), true);
    }
}
