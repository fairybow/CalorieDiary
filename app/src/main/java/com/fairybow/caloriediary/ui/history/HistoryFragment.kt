package com.fairybow.caloriediary.ui.history

import com.fairybow.caloriediary.databinding.FragmentHistoryBinding
import com.fairybow.caloriediary.ui.BaseFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryViewModel>(
    FragmentHistoryBinding::inflate,
    HistoryViewModel::class.java
)
