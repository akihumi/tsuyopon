
package ac.jp.itc.s11013.tsuyoponmonster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MonstersData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "monsters.db",
            TABLE_NAME = "monster",
            IMAGE = "image",
            STATUS_DATA = "status_data";
    public static final int DB_VERSIONS = 1;

    public MonstersData(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSIONS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                android.provider.BaseColumns._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IMAGE + " NONE, " + STATUS_DATA + " NONE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
