package pwcdma.asystentgierplanszowych;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends AppCompatActivity {

    private static final String TAB = "pwcdma.asystentgierplanszowych.tab";
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();
        addTab("Grupy", R.id.tab1);
        addTab("Gry", R.id.tab2);
        addTab("Mój profil", R.id.tab3);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.tab1, new GroupsFragment())
                .add(R.id.tab2, new GamesFragment())
                .commit();
        if (savedInstanceState != null){
            tabHost.setCurrentTab(savedInstanceState.getInt(TAB));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(TAB, tabHost.getCurrentTab());
    }

    private void addTab(String indicator, int content){
        TabSpec tab = tabHost.newTabSpec(indicator);
        tab.setIndicator(indicator);
        tab.setContent(content);
        tabHost.addTab(tab);
    }
}
