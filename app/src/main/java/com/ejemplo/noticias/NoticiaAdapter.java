package com.ejemplo.noticias;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.noticias.databinding.ItemNoticiaBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.color.MaterialColors;

import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder> {

    public interface OnNoticiaListener {
        void onLeer(Noticia noticia);
        void onFavorita(int posicion);
    }

    private final List<Noticia> noticias;
    private final OnNoticiaListener listener;

    public NoticiaAdapter(List<Noticia> noticias, OnNoticiaListener listener) {
        this.noticias = noticias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticiaViewHolder(ItemNoticiaBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        holder.mostrar(noticias.get(position));
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    class NoticiaViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoticiaBinding binding;
        // Ampliación 4: valores del estilo Elevated (el del XML) para poder volver a él
        private final float elevacionElevated;
        private final int fondoElevated;
        private final int fondoFilled;

        NoticiaViewHolder(ItemNoticiaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            MaterialCardView card = binding.getRoot();
            elevacionElevated = card.getCardElevation();
            fondoElevated = card.getCardBackgroundColor().getDefaultColor();
            // Mismo rol de color que usa Widget.Material3.CardView.Filled
            fondoFilled = MaterialColors.getColor(card,
                    com.google.android.material.R.attr.colorSurfaceContainerHighest);

            binding.btnLeer.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) listener.onLeer(noticias.get(pos));
            });
            binding.btnFavorita.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) listener.onFavorita(pos);
            });
        }

        void mostrar(Noticia n) {
            binding.tvTitulo.setText(n.getTitulo());
            binding.tvResumen.setText(n.getResumen());

            int texto, icono;
            switch (n.getCategoria()) {
                case Noticia.DEPORTES:
                    texto = R.string.seccion_deportes; icono = R.drawable.ic_deportes; break;
                case Noticia.TECNOLOGIA:
                    texto = R.string.seccion_tecnologia; icono = R.drawable.ic_tecnologia; break;
                default:
                    texto = R.string.seccion_cultura; icono = R.drawable.ic_cultura;
            }
            binding.chipCategoria.setText(texto);
            binding.chipCategoria.setChipIconResource(icono);

            binding.btnFavorita.setImageResource(
                    n.isFavorita() ? R.drawable.ic_star : R.drawable.ic_star_border);

            // Ampliación 4: Filled (sin sombra, fondo tonal) para favoritas; Elevated para el resto
            MaterialCardView card = binding.getRoot();
            if (n.isFavorita()) {
                card.setCardElevation(0f);
                card.setCardBackgroundColor(fondoFilled);
            } else {
                card.setCardElevation(elevacionElevated);
                card.setCardBackgroundColor(fondoElevated);
            }
        }
    }
}
