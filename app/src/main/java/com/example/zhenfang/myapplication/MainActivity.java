package com.example.zhenfang.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.zhenfang.myapplication.application.CacheApplication;
import com.example.zhenfang.myapplication.databaseImp.orm.bean.DBCache;
import com.example.zhenfang.myapplication.utils.BBAppLogger;
import com.example.zhenfang.myapplication.databaseImp.store.CacheStore;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    static  int RUN_LOOP = 1000;
    static  int RUN_LOOP_DB = 1000;
    static  int RUN_LOOP_DISK = 10000;
    private boolean LOG_CHECKED = true;
    CacheStore cacheStore;
    static final String CONST_VALUE =
            "The image 'ic_no_user.png' varies significantly in its density-independent (dip) size across the various density versions: drawable-mdpi/ic_no_user.png: 92x83 dp (92x83 px), drawable-xhdpi/ic_no_user.png: 69x63 dp (138x125 px), drawable-hdpi/ic_no_user.png: 69x62 dp (103x93 px), drawable-xxhdpi/ic_no_user.png: 69x62 dp (206x186 px)";


    // bench test
    private View.OnClickListener benchTest = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            RUN_LOOP = RUN_LOOP_DB = RUN_LOOP_DISK = 1000;
//            prepareAll();
//            readAll();

            RUN_LOOP = RUN_LOOP_DB = RUN_LOOP_DISK = 10000;
            prepareDB();
            prepareDiskLRU();
            prepareStoreApi();
            prepareLRU();
            readAll();

//            RUN_LOOP = RUN_LOOP_DB = RUN_LOOP_DISK = 100000;
//            prepareDiskLRU();
//            prepareStoreApi();
//            prepareLRU();
//            readAll();

        }
    };



    // read
    private View.OnClickListener readAllPreference = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           readPreference();
        }
    };



    private View.OnClickListener readAllDB = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readDB();
        }
    };


    private View.OnClickListener readAllDiskLRU = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readDISKLRU();
        }
    };
    private View.OnClickListener readAllStore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readStoreApi();
        }
    };

    private View.OnClickListener readAllLRU = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readLru();
        }
    };


    private View.OnClickListener readAllData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readPreference();
            readDB();
            readDISKLRU();
            readStoreApi();
            readLru();
        }
    };


    // write
    private View.OnClickListener writeAllPreference = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareSharePreference();
        }
    };
    private View.OnClickListener writeAllDB = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareDB();
        }
    };
    private View.OnClickListener writeAllRu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareDiskLRU();
        }
    };
    private View.OnClickListener writeAllStoreApi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareStoreApi();
        }
    };

    private View.OnClickListener writeAllLRU = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareLRU();
        }
    };

    private View.OnClickListener writeAllData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            prepareSharePreference();
            prepareDB();
            prepareDiskLRU();
            prepareStoreApi();
            prepareLRU();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));
        cacheStore = CacheApplication.get().getCacheStore();
    }

    private void prepareAll() {
        prepareSharePreference();
        prepareDB();
        prepareDiskLRU();
        prepareStoreApi();
        prepareLRU();
    }

    private void prepareSharePreference() {
        try {
            long t = System.currentTimeMillis();
            int now = (int) (t / 1000);
            for (int i = 0; i < RUN_LOOP; ++i) {
                String tmp = Integer.toString(i);
                cacheStore.String(tmp).put(tmp + CONST_VALUE).apply();
            }
            t = System.currentTimeMillis();
            int then = (int) (t / 1000);
            BBAppLogger.d("time measure write: sharePreference use time: %d -- loop %d", then - now,
                    RUN_LOOP);
        } catch (OutOfMemoryError e) {
            System.gc();
            BBAppLogger.d("time measure write :sharePreference %d crashed", RUN_LOOP);
        }
    }

    private void prepareDB() {
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        List<DBCache> keyValueList = new ArrayList<>(RUN_LOOP_DB);
        for (int i = 0; i < RUN_LOOP_DB; ++i) {
            keyValueList.add(CacheStore.composeCache(Integer.toString(i), i + CONST_VALUE));
        }
        cacheStore.save(keyValueList);
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger.d("time measure write: db use time: %d -- loop %d", then - now, RUN_LOOP_DB);
    }

    private void prepareDiskLRU() {
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            cacheStore.put(tmp, tmp + CONST_VALUE);
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger
                .d("time measure write: disklru use time: %d -- loop %d", then - now, RUN_LOOP_DISK);
    }

    private void prepareStoreApi() {
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            cacheStore.storeSave(tmp, tmp + CONST_VALUE);
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger.d("time measure write: store save use time: %d -- loop %d", then - now,
                RUN_LOOP_DISK);
    }

    private void prepareLRU() {
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            cacheStore.LRUSave(tmp, tmp + CONST_VALUE);
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger.d("time measure write: LRU save use time: %d -- loop %d", then - now,
                RUN_LOOP_DISK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void setContentView(View view) {
        View mView = view;
        Button buttonBenchTest = (Button)mView.findViewById(R.id.button_bench_test);
        buttonBenchTest.setOnClickListener(benchTest);


        final CheckBox log = (CheckBox) mView.findViewById(R.id.read_with_log);
        log.setChecked(LOG_CHECKED);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LOG_CHECKED = log.isChecked();
            }
        });

        // write
        Button writePreference = (Button)mView.findViewById(R.id.button_write_share);
        writePreference.setOnClickListener(writeAllPreference);

        Button writeDb= (Button)mView.findViewById(R.id.button_write_db);
        writeDb.setOnClickListener(writeAllDB);

        Button writeRu= (Button)mView.findViewById(R.id.button_write_diskru);
        writeRu.setOnClickListener(writeAllRu);

        Button writeStore = (Button)mView.findViewById(R.id.button_write_store);
        writeStore.setOnClickListener(writeAllStoreApi);

        Button writeLRU = (Button)mView.findViewById(R.id.button_write_lru);
        writeLRU.setOnClickListener(writeAllLRU);

        Button writeAll= (Button)mView.findViewById(R.id.button_write_all);
        writeAll.setOnClickListener(writeAllData);


        // button
        Button preference = (Button)mView.findViewById(R.id.button_read_share);
        preference.setOnClickListener(readAllPreference);

        Button db = (Button) mView.findViewById(R.id.button_read_db);
        db.setOnClickListener(readAllDB);

        Button ru = (Button) mView.findViewById(R.id.button_read_diskru);
        ru.setOnClickListener(readAllDiskLRU);

        Button store = (Button) mView.findViewById(R.id.button_read_store_api);
        store.setOnClickListener(readAllStore);

        Button lru = (Button) mView.findViewById(R.id.button_read_lru);
        lru.setOnClickListener(readAllLRU);

        Button readAll = (Button) mView.findViewById(R.id.button_read_all);
        readAll.setOnClickListener(readAllData);

        super.setContentView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void readPreference() {
        CacheStore cacheStore = CacheApplication.get().getCacheStore();
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        for (int i = 0; i < RUN_LOOP; ++i) {
            String tmp = Integer.toString(i);
            if (LOG_CHECKED) {
                BBAppLogger.d("time measure read: sharePreference(%s, %s)", tmp,
                        cacheStore.String(tmp).getOr(tmp));
            } else {
                cacheStore.String(tmp).getOr(tmp);
            }
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger.d("time measure read: sharePreference use time: %d -- loop %d", then - now,
                RUN_LOOP);

    }

    private void readDB() {
        CacheStore cacheStore = CacheApplication.get().getCacheStore();
        long t = System.currentTimeMillis();
        int now = (int)(t / 1000);
        for (int i = 0; i < RUN_LOOP_DB; ++i) {
            String tmp = Integer.toString(i);
            if (LOG_CHECKED) {
                BBAppLogger.d("time measure read: db(%s, %s)", tmp, cacheStore.getDb(tmp));
            } else {
                cacheStore.getDb(tmp);
            }
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger.d("time measure read: db use time: %d -- loop %d", then - now, RUN_LOOP_DB);
    }
    private void readDISKLRU() {
        CacheStore cacheStore = CacheApplication.get().getCacheStore();
        long t = System.currentTimeMillis();
        int now = (int) (t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            if (LOG_CHECKED) {
                BBAppLogger.d("time measure read: disklru(%s, %s)", tmp, cacheStore.getFromRu(tmp));
            } else {
                cacheStore.getFromRu(tmp);
            }
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger
                .d("time measure read: disklru use time: %d -- loop %d", then - now, RUN_LOOP_DISK);

    }

    private void readStoreApi() {
        CacheStore cacheStore = CacheApplication.get().getCacheStore();
        long t = System.currentTimeMillis();
        int now = (int) (t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            if (LOG_CHECKED) {
                BBAppLogger.d("time measure read: store api(%s, %s)", tmp,
                        cacheStore.getStoreValue(tmp));
            } else {
                cacheStore.getStoreValue(tmp);
            }
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger
                .d("time measure read: store api use time: %d -- loop %d", then - now,
                        RUN_LOOP_DISK);

    }

    private void readLru() {
        CacheStore cacheStore = CacheApplication.get().getCacheStore();
        long t = System.currentTimeMillis();
        int now = (int) (t / 1000);
        for (int i = 0; i < RUN_LOOP_DISK; ++i) {
            String tmp = Integer.toString(i);
            if (LOG_CHECKED) {
                BBAppLogger.d("time measure read: LRU(%s, %s)", tmp,
                        cacheStore.LURGet(tmp));
            } else {
                cacheStore.LURGet(tmp);
            }
        }
        t = System.currentTimeMillis();
        int then = (int) (t / 1000);
        BBAppLogger
                .d("time measure read: LRU use time: %d -- loop %d", then - now,
                        RUN_LOOP_DISK);

    }

    private void readAll() {
        readPreference();
        readDB();
        readDISKLRU();
        readStoreApi();
        readLru();
    }

}
