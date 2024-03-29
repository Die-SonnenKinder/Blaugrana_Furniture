package com.example.blaugranafurniture.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blaugranafurniture.data.Product
import com.example.blaugranafurniture.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    init {
        fetchSpecialProducts()
    }

    private fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialProducts.value = Resource.Loading()
            firestore.collection("Products")
                .whereEqualTo("category","Special Products")
                .get()
                .addOnSuccessListener { result ->
                    val specialProductList = result.toObjects(Product::class.java)
                    _specialProducts.value = Resource.Success(specialProductList)
                }
                .addOnFailureListener { exception ->
                    _specialProducts.value = Resource.Error(exception.message.toString())
                }
        }
    }
}
