package org.literacyapp.word;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.literacyapp.R;
import org.literacyapp.dao.DaoMaster;
import org.literacyapp.dao.DaoSession;
import org.literacyapp.dao.Number;
import org.literacyapp.dao.Word;
import org.literacyapp.dao.WordDao;
import org.literacyapp.util.Log;

import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private WordDao wordDao;

    private TextView mTextViewWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(getClass(), "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_word_list);

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "literacyapp", null);
        db = openHelper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        wordDao = daoSession.getWordDao();

        mTextViewWordList = (TextView) findViewById(R.id.textViewWordList);
    }

    @Override
    protected void onStart() {
        Log.d(getClass(), "onStart");
        super.onStart();

        String numberListText = "";
        List<Word> words = wordDao.loadAll();
        for (Word word : words) {
            numberListText += "id: " + word.getId() + ", serverId: " + word.getServerId() + ", text: " + word.getText() + "\n";
        }
        mTextViewWordList.setText(numberListText);
    }
}