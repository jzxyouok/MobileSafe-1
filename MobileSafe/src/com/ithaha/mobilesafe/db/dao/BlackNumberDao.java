package com.ithaha.mobilesafe.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ithaha.mobilesafe.db.BlackNumberDBOpenHelper;
import com.ithaha.mobilesafe.domain.BlackNumberInfo;

/**
 * ���������ݿ��CRUD
 * @author hello
 *
 */
public class BlackNumberDao {
	private BlackNumberDBOpenHelper helper;
	/**
	 * ���췽��
	 * @param context
	 */
	public BlackNumberDao(Context context) {
		helper = new BlackNumberDBOpenHelper(context);
	}
	
	/**
	 * ��ѯ�����������Ƿ����
	 * @param number
	 * @return
	 */
	public boolean find(String number) {
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from blacknumber where number = ?", new String[]{number});
		while(cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/**
	 * ��ѯ���������������ģʽ
	 * @param number
	 * @return ���غ��������ģʽ
	 */
	public String findMode(String number) {
		String result = "";
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select mode from blacknumber where number = ?", new String[]{number});
		while(cursor.moveToNext()) {
			result = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/**
	 * ���Ӻ���������
	 * @param number	����������
	 * @param mode		����ģʽ 1.�绰���� 2.�������� 3.������
	 */
	public void add(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert("blacknumber", null, values);
		db.close();
	}
	
	/**
	 * �޸ĺ��������������ģʽ
	 * @param number
	 * @param mode
	 */
	public void update(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", mode);
		db.update("blacknumber", values, "number = ?", new String[]{number});
		db.close();
	}
	
	/**
	 * ɾ������������
	 * @param number
	 */
	public void delete(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("blacknumber", "number = ?", new String[]{number});
		db.close();
	}
	
	/**
	 * ��ѯȫ������������
	 * @param number
	 * @return
	 */
	public List<BlackNumberInfo> findAll() {
		List<BlackNumberInfo> result = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber order by _id desc", null);
		while(cursor.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			String number = cursor.getString(0);
			String mode = cursor.getString(1);
			info.setMode(mode);
			info.setNumber(number);
			result.add(info);
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/**
	 * ��ѯ���ֵĺ���������
	 * @param offset ���ĸ�λ�ÿ�ʼ��ȡ����
	 * @param maxnumber һ������ȡ��������¼
	 * @return
	 */
	public List<BlackNumberInfo> findPart(int offset, int maxnumber) {
		List<BlackNumberInfo> result = new ArrayList<BlackNumberInfo>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blacknumber order by _id desc limit ? offset ?", new String[]{String.valueOf(maxnumber),String.valueOf(offset)});
		while(cursor.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			String number = cursor.getString(0);
			String mode = cursor.getString(1);
			info.setMode(mode);
			info.setNumber(number);
			result.add(info);
		}
		cursor.close();
		db.close();
		return result;
	}
	
}