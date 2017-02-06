package dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import entity.DownLoadChapterInfo;

/**
 * Created by xiao on 2016/8/30.
 */

public class DownLoadChapterInfoDao extends BaseDao<DownLoadChapterInfo,Integer> {
    public DownLoadChapterInfoDao(Context context) {
        super(context);
    }

    @Override
    public Dao<DownLoadChapterInfo, Integer> getDao() throws SQLException {
        return getHelper().getDao(DownLoadChapterInfo.class);
    }


}
//public class CashFlowDao extends BaseDao<CashFlow, Integer> {</p><p> public CashFlowDao(Context context) {
//    super(context);
//}</p><p> @Override
//public Dao<CashFlow, Integer> getDao() throws SQLException {
//    return getHelper().getDao(CashFlow.class);
//}