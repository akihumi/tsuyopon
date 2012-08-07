
package ac.jp.itc.s11013.tsuyoponmonster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MonstersData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "monsters.db";
    public static final int DB_VERSIONS = 1;

    public MonstersData(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSIONS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBoperation.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
