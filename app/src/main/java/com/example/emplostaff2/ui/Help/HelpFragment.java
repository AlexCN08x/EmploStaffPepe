package com.example.emplostaff2.ui.Help;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.emplostaff2.databinding.FragmentHelpBinding;
import java.util.ArrayList;
import java.util.List;

public class HelpFragment extends Fragment {

    private FragmentHelpBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HelpViewModel helpViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);

        binding = FragmentHelpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<FAQItem> faqList = new ArrayList<>();
        faqList.add(new FAQItem("¿Como puedo ver mi salario en la aplicacion con sus respectivos datos?", "Debe ir al apartado 'Salary' y ahi podra visualizar los datos deseados sobre el salario"));
        faqList.add(new FAQItem("De que forma podria cambiar la contraseña", "En la pagina de inicio de sesion, puede comprobar que hay un boton de 'Reset', haga click y cambie su contraseña"));
        faqList.add(new FAQItem("Alguna otra question", "Si tiene otro tipo de duda, llame al 677 776 888 para que podamos resolverla"));


        FAQAdapter adapter = new FAQAdapter(faqList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


