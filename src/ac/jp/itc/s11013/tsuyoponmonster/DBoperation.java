
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBoperation {

    private static final String TABLE_NAME = "monster",
            IMAGE = "image", MONSTER_NAME = "name";

    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "(" +
                    android.provider.BaseColumns._ID + " string primary key, " +
                    IMAGE + " blob, " + MONSTER_NAME + " string, " +
                    Monster.STATUS_STRENGTH + " integer, " +
                    Monster.STATUS_POWER + " integer, " +
                    Monster.STATUS_QUICKNESS + " integer, " +
                    Monster.STATUS_KIND + " integer, " +
                    Monster.STATUS_STOMACH_GAUGE + " integer, " +
                    Monster.STATUS_LUCK + " integer, " +
                    Monster.STATUS_LIFE + " integer);";

    public static long insert(Context context, byte[] blob, Monster monster) {
        HashMap<String, Integer> status = monster.getStatusList();
        SQLiteDatabase db = null;
        long result = 0;
        try {
            MonstersData monsterdb = new MonstersData(context);
            db = monsterdb.getWritableDatabase();
            ContentValues cv = new ContentValues(10);
            cv.put(android.provider.BaseColumns._ID, monster.getId());
            cv.put(IMAGE, blob);
            cv.put(MONSTER_NAME, monster.getName());
            cv.put(Monster.STATUS_STRENGTH, status.get(Monster.STATUS_STRENGTH));
            cv.put(Monster.STATUS_POWER, status.get(Monster.STATUS_POWER));
            cv.put(Monster.STATUS_QUICKNESS, status.get(Monster.STATUS_QUICKNESS));
            cv.put(Monster.STATUS_KIND, status.get(Monster.STATUS_KIND));
            cv.put(Monster.STATUS_STOMACH_GAUGE, status.get(Monster.STATUS_STOMACH_GAUGE));
            cv.put(Monster.STATUS_LUCK, status.get(Monster.STATUS_LUCK));
            cv.put(Monster.STATUS_LIFE, status.get(Monster.STATUS_LIFE));
            result =  db.insert(TABLE_NAME, null, cv);
//
//            String sql = "insert into " + TABLE_NAME +" (" +
//            android.provider.BaseColumns._ID + ", " + IMAGE + ", " + MONSTER_NAME +
//            Monster.STATUS_STRENGTH + ", " + Monster.STATUS_POWER + ", " +
//            Monster.STATUS_QUICKNESS + ", " + Monster.STATUS_KIND +", " +
//            Monster.STATUS_STOMACH_GAUGE + ", " + Monster.STATUS_LUCK + ", " +
//            Monster.STATUS_LIFE + ") " + "values(\'" + monster.getId() + "\', " + blob + ", \'" +
//            monster.getName() + "\', " + status.get(Monster.STATUS_STRENGTH) + ", " +
//            status.get(Monster.STATUS_POWER) + ", " + status.get(Monster.STATUS_QUICKNESS) + ", " +
//            status.get(Monster.STATUS_KIND) + ", " + status.get(Monster.STATUS_STOMACH_GAUGE) + ", " +
//            status.get(Monster.STATUS_LUCK) + ", " +status.get(Monster.STATUS_LIFE) + ");";
//            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result;
    }
    public static byte[] getBlob(Context context, String id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            MonstersData monsterdb = new MonstersData(context);
            db = monsterdb.getReadableDatabase();
            String sql = String.format("select %s from %s where %s = ?",
                    IMAGE, TABLE_NAME, android.provider.BaseColumns._ID);
            String[] args = {id};
            cursor = db.rawQuery(sql, args);
            if (cursor.moveToFirst()) {
                return cursor.getBlob(0);
            }
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
    public static Monster getMonster(Context context, String id){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Monster monster = null;
        try{
            MonstersData monstersdb = new MonstersData(context);
            db = monstersdb.getReadableDatabase();
            String sql = String.format("select %s, %s, %s, %s, %s, %s, %s, %s from %s where %s = ?",
                    MONSTER_NAME, Monster.STATUS_STRENGTH, Monster.STATUS_POWER,
                    Monster.STATUS_QUICKNESS, Monster.STATUS_KIND, Monster.STATUS_STOMACH_GAUGE,
                    Monster.STATUS_LUCK, Monster.STATUS_LIFE, TABLE_NAME, android.provider.BaseColumns._ID);
            String[] columns = {id};
            cursor = db.rawQuery(sql, columns);
            monster = new Monster(id, cursor.getString(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
        }finally{
            if(db != null){
                db.close();
            }
        }
        return monster;
    }
    public static ArrayList<String> getId(Context context){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ArrayList<String> id_list = new ArrayList<String>();
        try{
            MonstersData monstersdb = new MonstersData(context);
            db = monstersdb.getReadableDatabase();
            String[] culumns = {android.provider.BaseColumns._ID};
            String sql = String.format("select ? from %s",
                    TABLE_NAME);
            cursor = db.rawQuery(sql, culumns);
            while(cursor.moveToNext()){
                
            }
        }finally{
            if(db != null){
                db.close();
            }
        }
        return null;
    }
}