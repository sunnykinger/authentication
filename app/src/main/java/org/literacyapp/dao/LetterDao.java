package org.literacyapp.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Calendar;
import org.literacyapp.dao.converter.CalendarConverter;
import org.literacyapp.dao.converter.ContentStatusConverter;
import org.literacyapp.dao.converter.LocaleConverter;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.content.ContentStatus;

import org.literacyapp.model.content.Letter;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LETTER".
*/
public class LetterDao extends AbstractDao<Letter, Long> {

    public static final String TABLENAME = "LETTER";

    /**
     * Properties of entity Letter.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Locale = new Property(1, String.class, "locale", false, "LOCALE");
        public final static Property TimeLastUpdate = new Property(2, Long.class, "timeLastUpdate", false, "TIME_LAST_UPDATE");
        public final static Property RevisionNumber = new Property(3, Integer.class, "revisionNumber", false, "REVISION_NUMBER");
        public final static Property ContentStatus = new Property(4, String.class, "contentStatus", false, "CONTENT_STATUS");
        public final static Property Text = new Property(5, String.class, "text", false, "TEXT");
        public final static Property UsageCount = new Property(6, int.class, "usageCount", false, "USAGE_COUNT");
    }

    private final LocaleConverter localeConverter = new LocaleConverter();
    private final CalendarConverter timeLastUpdateConverter = new CalendarConverter();
    private final ContentStatusConverter contentStatusConverter = new ContentStatusConverter();
    private Query<Letter> audio_LettersQuery;
    private Query<Letter> image_LettersQuery;
    private Query<Letter> video_LettersQuery;

    public LetterDao(DaoConfig config) {
        super(config);
    }
    
    public LetterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LETTER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LOCALE\" TEXT NOT NULL ," + // 1: locale
                "\"TIME_LAST_UPDATE\" INTEGER," + // 2: timeLastUpdate
                "\"REVISION_NUMBER\" INTEGER NOT NULL ," + // 3: revisionNumber
                "\"CONTENT_STATUS\" TEXT NOT NULL ," + // 4: contentStatus
                "\"TEXT\" TEXT NOT NULL ," + // 5: text
                "\"USAGE_COUNT\" INTEGER NOT NULL );"); // 6: usageCount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LETTER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Letter entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, localeConverter.convertToDatabaseValue(entity.getLocale()));
 
        Calendar timeLastUpdate = entity.getTimeLastUpdate();
        if (timeLastUpdate != null) {
            stmt.bindLong(3, timeLastUpdateConverter.convertToDatabaseValue(timeLastUpdate));
        }
        stmt.bindLong(4, entity.getRevisionNumber());
        stmt.bindString(5, contentStatusConverter.convertToDatabaseValue(entity.getContentStatus()));
        stmt.bindString(6, entity.getText());
        stmt.bindLong(7, entity.getUsageCount());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Letter entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, localeConverter.convertToDatabaseValue(entity.getLocale()));
 
        Calendar timeLastUpdate = entity.getTimeLastUpdate();
        if (timeLastUpdate != null) {
            stmt.bindLong(3, timeLastUpdateConverter.convertToDatabaseValue(timeLastUpdate));
        }
        stmt.bindLong(4, entity.getRevisionNumber());
        stmt.bindString(5, contentStatusConverter.convertToDatabaseValue(entity.getContentStatus()));
        stmt.bindString(6, entity.getText());
        stmt.bindLong(7, entity.getUsageCount());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Letter readEntity(Cursor cursor, int offset) {
        Letter entity = new Letter( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            localeConverter.convertToEntityProperty(cursor.getString(offset + 1)), // locale
            cursor.isNull(offset + 2) ? null : timeLastUpdateConverter.convertToEntityProperty(cursor.getLong(offset + 2)), // timeLastUpdate
            cursor.getInt(offset + 3), // revisionNumber
            contentStatusConverter.convertToEntityProperty(cursor.getString(offset + 4)), // contentStatus
            cursor.getString(offset + 5), // text
            cursor.getInt(offset + 6) // usageCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Letter entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLocale(localeConverter.convertToEntityProperty(cursor.getString(offset + 1)));
        entity.setTimeLastUpdate(cursor.isNull(offset + 2) ? null : timeLastUpdateConverter.convertToEntityProperty(cursor.getLong(offset + 2)));
        entity.setRevisionNumber(cursor.getInt(offset + 3));
        entity.setContentStatus(contentStatusConverter.convertToEntityProperty(cursor.getString(offset + 4)));
        entity.setText(cursor.getString(offset + 5));
        entity.setUsageCount(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Letter entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Letter entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Letter entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "letters" to-many relationship of Audio. */
    public List<Letter> _queryAudio_Letters(Long id) {
        synchronized (this) {
            if (audio_LettersQuery == null) {
                QueryBuilder<Letter> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Id.eq(null));
                audio_LettersQuery = queryBuilder.build();
            }
        }
        Query<Letter> query = audio_LettersQuery.forCurrentThread();
        query.setParameter(0, id);
        return query.list();
    }

    /** Internal query to resolve the "letters" to-many relationship of Image. */
    public List<Letter> _queryImage_Letters(Long id) {
        synchronized (this) {
            if (image_LettersQuery == null) {
                QueryBuilder<Letter> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Id.eq(null));
                image_LettersQuery = queryBuilder.build();
            }
        }
        Query<Letter> query = image_LettersQuery.forCurrentThread();
        query.setParameter(0, id);
        return query.list();
    }

    /** Internal query to resolve the "letters" to-many relationship of Video. */
    public List<Letter> _queryVideo_Letters(Long id) {
        synchronized (this) {
            if (video_LettersQuery == null) {
                QueryBuilder<Letter> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Id.eq(null));
                video_LettersQuery = queryBuilder.build();
            }
        }
        Query<Letter> query = video_LettersQuery.forCurrentThread();
        query.setParameter(0, id);
        return query.list();
    }

}
