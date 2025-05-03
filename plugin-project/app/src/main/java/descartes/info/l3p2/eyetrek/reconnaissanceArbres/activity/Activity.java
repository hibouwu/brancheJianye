package descartes.info.l3p2.eyetrek.reconnaissanceArbres.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tencent.shadow.core.runtime.container.DelegateProviderHolder;

import descartes.info.l3p2.eyetrek.reconnaissanceArbres.R;
import descartes.info.l3p2.eyetrek.reconnaissanceArbres.fragments.FragmentEspecesProches;

/**
 * Cette classe s'occupe de l'activité du module Arbres proches.
 *
 * @author Huihui HUANG - 2021
 *
 * @author Lin Zhanwang - 10/04/2025
 * Suppression de 'transaction.add(R.id.settings, new FragmentExitModule());'
 * dans le but de transformer le module dynamique en application indépendante.
 */
public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Activity", "onCreate");
        Log.d("Activity", "DelegateProviderCheck:provider=" + DelegateProviderHolder.getDelegateProvider(DelegateProviderHolder.DEFAULT_KEY));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_module);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_main, new FragmentEspecesProches());
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}