package be.technifuture.tff.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.BoutiqueAdapter
import be.technifuture.tff.adapter.PanierAdapter
import be.technifuture.tff.databinding.FragmentMarketBinding
import be.technifuture.tff.model.PanierItemBoutique
import be.technifuture.tff.model.interfaces.BoutiqueListener
import be.technifuture.tff.repos.ReposShop

class MarketFragment : Fragment(), BoutiqueListener {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var adapterBoutique : BoutiqueAdapter
    private lateinit var adapterPanier : PanierAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupBoutiqueRecyclerView()
        SetupPanierRecyclerView()

        binding.btnHome.setOnClickListener {
            val direction = MarketFragmentDirections.actionMarketFragmentToJeuxFragment()
            findNavController().navigate(direction)
        }
    }

    private fun SetupBoutiqueRecyclerView(){
        ReposShop.getInstance().shop?.let { it ->
            adapterBoutique = BoutiqueAdapter(it, this)
            binding.recyclerViewBoutique.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
            binding.recyclerViewBoutique.adapter = adapterBoutique
            adapterBoutique.notifyDataSetChanged()
        }
    }
    private fun SetupPanierRecyclerView(){
        ReposShop.getInstance().panier?.let { it ->
            adapterPanier = PanierAdapter(it, this)
            binding.recyclerViewPanier.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
            binding.recyclerViewPanier.adapter = adapterPanier
            adapterPanier.notifyDataSetChanged()
        }

    }

    override fun onBonusClick(action: String, item: PanierItemBoutique) {
        if(action == "ADD_TO_PANIER"){
            ReposShop.getInstance().panierAddFromShop(item);
        }
        if(action == "ADD_PANIER"){
            ReposShop.getInstance().panierAdd(item);
        }
        if(action == "DEL_PANIER"){
            ReposShop.getInstance().panierDel(item)
        }
        if(action == "REMOVE_PANIER"){
            ReposShop.getInstance().panierRemove(item);
        }
        adapterPanier.notifyDataSetChanged()
        binding.txtPrixTotal.text = ReposShop.getInstance().getTotal()
    }
}