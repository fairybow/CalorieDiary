package com.fairybow.caloriediary.ui.catalog

import com.fairybow.caloriediary.databinding.FragmentCatalogBinding
import com.fairybow.caloriediary.ui.BaseFragment

class CatalogFragment : BaseFragment<FragmentCatalogBinding, CatalogViewModel>(
    FragmentCatalogBinding::inflate,
    CatalogViewModel::class.java
)
