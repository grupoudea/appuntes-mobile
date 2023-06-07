package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.model.Apunte;

public class ApunteAdapter extends RecyclerView.Adapter<ApunteAdapter.ViewHolder> {

    private static List<Apunte> apuntes = new ArrayList<>();
    private HashSet<Integer> expandedItems;


    public ApunteAdapter(List<Apunte> apuntesList) {
        apuntes = apuntesList;
        this.expandedItems = new HashSet<>();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewContenido;
        private final Button buttonMenuApunte;
        private final CardView cardView;
        private final TextView viewMoreButton;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
            });
            textViewContenido = v.findViewById(R.id.textViewContenido);
            buttonMenuApunte = v.findViewById(R.id.buttonMenuApunte);

            cardView = v.findViewById(R.id.cardView);
            viewMoreButton = v.findViewById(R.id.viewMoreButton);

            buttonMenuApunte.setOnClickListener(view -> {
                showMenu(view.getContext());
            });
        }
        public TextView getTextViewContenido() {
            return textViewContenido;
        }

        public Button getButtonMenuApunte() {
            return buttonMenuApunte;
        }

        private void showMenu(Context context) {
            PopupMenu popup = new PopupMenu(context, getButtonMenuApunte());
            popup.getMenuInflater().inflate(R.menu.menu_opciones_apunte, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                int position = getAdapterPosition();
                Apunte apunte = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("apunte", apunte);

                switch (item.getItemId()) {
                    case R.id.menuCompartirApunte:
                        if (position != RecyclerView.NO_POSITION) {

                        }
                        return true;
                    case R.id.menuPublicarApunte:

                    default:
                        return false;
                }
            });
            popup.show();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apunte_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String message = getItem(position).getContenido();

        final int maxLines = 3;
        viewHolder.textViewContenido.setMaxLines(maxLines);
        viewHolder.textViewContenido.setText(message);
        viewHolder.textViewContenido.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int linesButton = viewHolder.textViewContenido.getLayout().getLineCount();
                if (viewHolder.textViewContenido.getLayout().getEllipsisCount(linesButton-1) > 0) {
                    viewHolder.viewMoreButton.setVisibility(View.VISIBLE);
                } else if (linesButton > maxLines ) {
                    viewHolder.viewMoreButton.setText("Ver menos");
                } else {
                    viewHolder.viewMoreButton.setVisibility(View.GONE);
                }
                //viewHolder.textViewContenido.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        viewHolder.viewMoreButton.setOnClickListener(new View.OnClickListener() {
            boolean isExpanded = false;

            @Override
            public void onClick(View view) {
                 if (isExpanded || viewHolder.viewMoreButton.getText().equals("Ver menos")) {
                    viewHolder.textViewContenido.setMaxLines(3);
                    viewHolder.viewMoreButton.setText("Ver más");
                } else {
                    int newCountLine =  viewHolder.textViewContenido.getMaxLines() + 3;
                    viewHolder.textViewContenido.setMaxLines(newCountLine);
                    int linesButton = viewHolder.textViewContenido.getLayout().getLineCount();
                    int ellipsi = viewHolder.textViewContenido.getLayout().getEllipsisCount(linesButton-1);
                    if ( ellipsi > 0) {
                        viewHolder.viewMoreButton.setText("Ver más");
                        isExpanded = false;
                    }else {
                        viewHolder.viewMoreButton.setText("Ver menos");
                        isExpanded = !isExpanded;

                    }
                }
            }
        });


        //viewHolder.getTextViewContenido().setText(getItem(position).getContenido());
    }

    @Override
    public int getItemCount() {
        int size = apuntes.size();
        return size;
    }

    public static Apunte getItem(int position) {
        return apuntes.get(position);
    }
}
