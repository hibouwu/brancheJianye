package descartes.info.l3p2.modularite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import descartes.info.l3p2.MainActivity;
import descartes.info.l3p2.R;
/**
 * Fragment de menu pour l'application.
 * Cette classe gère l'affichage et la gestion des modules disponibles dans l'application.
 * 
 * @author Shi Jianye
 */
public class FragmentMenuModules extends Fragment {

    private RecyclerView recyclerView;
    private ModuleAdapter adapter;
    private List<Module> moduleItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        // 加载菜单碎片布局
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // 初始化RecyclerView
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        // 准备模块数据
        moduleItems = new ArrayList<>();
        prepareModuleData();
        
        // 设置适配器
        adapter = new ModuleAdapter(moduleItems);
        recyclerView.setAdapter(adapter);
    }
    
    // 准备模块数据
    private void prepareModuleData() {
        // 添加Arbre模块
        moduleItems.add(new Module(
                R.drawable.bouton_arbre,
                "Arbres prochese",
                v -> startArbrePlugin(v)
        ));

        // 添加Empreintes模块
        moduleItems.add(new Module(
                R.drawable.bouton_empreintes,
                "Empreintes",
                v -> startEmpreintesPlugin(v)
        ));
    }
    
    // 启动Arbre插件的方法
    private void startArbrePlugin(View view) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startArbrePlugin(view);
        }
    }

    // 启动Empreintes插件的方法
    private void startEmpreintesPlugin(View view) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startEmpreintesPlugin(view);
        }
    }
}
