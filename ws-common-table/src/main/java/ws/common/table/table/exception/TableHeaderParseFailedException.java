package ws.common.table.table.exception;

import ws.common.table.table.interfaces.table.TableHeader;

import java.io.File;

/**
 * Created by lee on 16-8-15.
 */
public class TableHeaderParseFailedException extends Exception {

    private static final long serialVersionUID = 1L;

    public TableHeaderParseFailedException(TableHeader header, File file) {
        this(header, file, (Throwable) null);
    }

    public TableHeaderParseFailedException(TableHeader header, File file, Throwable cause) {
        super("Can\'t Parse Table Header {" + "filePath: \"" + file.getPath() + "\"" + "}", cause);
    }
}
