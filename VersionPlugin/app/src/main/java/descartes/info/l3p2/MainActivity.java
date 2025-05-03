package descartes.info.l3p2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import descartes.info.l3p2.base.MyApplication;
import descartes.info.l3p2.modularite.FragmentMenuModules;
import descartes.info.l3p2.DownloadManager.DownloadManagerForPlugin;
import descartes.info.l3p2.utils.ModuleLoader;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;
/**
 * Activité principale de l'application qui sert de point d'entrée.
 * Cette classe affiche l'interface utilisateur principale et gère
 * le lancement des différents plugins disponibles dans l'application.
 * 
 * @author Shi Jianye
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Charger le fragment de menu dans le conteneur principal
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contenu_fragment, new FragmentMenuModules())
                .commit();
        
        // Configurer l'événement de clic pour le bouton de boussole
        CardView compassButton = findViewById(R.id.compass);
        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: fragment de boussole 
            }
        });
    }

    public void startArbrePlugin(View view) {
        ModuleLoader.loadArbrePlugin(this, new EnterCallback() {
            @Override
            public void onShowLoadingView(View view) {
                // Appelé dans PluginManager.MyPluginManager.enter
            }

            @Override
            public void onCloseLoadingView() {
                // Fermer la boîte de dialogue de chargement après le chargement
            }

            @Override
            public void onEnterComplete() {
                // Entrée réussie dans le plugin
            }
        });
    }

    public void startEmpreintesPlugin(View view) {
        ModuleLoader.loadEmpreintesPlugin(this, new EnterCallback() {
            @Override
            public void onShowLoadingView(View view) {
                // Appelé dans PluginManager.MyPluginManager.enter
            }

            @Override
            public void onCloseLoadingView() {
                // Fermer la boîte de dialogue de chargement après le chargement
            }

            @Override
            public void onEnterComplete() {
                // Entrée réussie dans le plugin
            }
        });
    }
}