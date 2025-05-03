package descartes.info.l3p2.modularite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import descartes.info.l3p2.R;

/**
 * Adaptateur pour les modules de l'application.
 * Cette classe gère la liaison des données aux vues des modules dans le RecyclerView.
 * 
 * @author Shi Jianye
 */

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    
    private final List<Module> items;
    
    public ModuleAdapter(List<Module> items) {
        this.items = items;
    }
    
    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module item = items.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.textView.setText(item.getTitle());
        
        if (item.getOnClickListener() != null) {
            holder.cardView.setOnClickListener(item.getOnClickListener());
        }
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    static class ModuleViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        
        ModuleViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.module_card);
            imageView = itemView.findViewById(R.id.module_image);
            textView = itemView.findViewById(R.id.module_title);
        }
    }
} 