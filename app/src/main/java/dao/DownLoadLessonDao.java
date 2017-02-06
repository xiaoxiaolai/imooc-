package dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import entity.DownLoadLesson;

/**
 * Created by xiao on 2016/8/30.
 */

public class DownLoadLessonDao extends BaseDao<DownLoadLesson,Integer> {
    public DownLoadLessonDao(Context context) {
        super(context);
    }

    @Override
    public Dao<DownLoadLesson, Integer> getDao() throws SQLException {
        return getHelper().getDao(DownLoadLesson.class);
    }


}
//public class CashFlowDao extends BaseDao<CashFlow, Integer> {</p><p> public CashFlowDao(Context context) {
//    super(context);
//}</p><p> @Override
//public Dao<CashFlow, Integer> getDao() throws SQLException {
//    return getHelper().getDao(CashFlow.class);
//}