package com.nammamistri.app.ui.site

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamistri.app.R
import com.nammamistri.app.data.model.Site
import com.nammamistri.app.databinding.ActivitySiteManagementBinding
import com.nammamistri.app.viewmodel.SiteViewModel

/**
 * Activity for managing construction sites
 * Features:
 * - Full-screen RecyclerView list of sites
 * - Add new sites
 * - Mark sites complete
 * - Delete sites
 */
class SiteManagementActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySiteManagementBinding
    private val siteViewModel: SiteViewModel by viewModels()
    
    private lateinit var siteAdapter: SiteAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiteManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle("ಸೈಟ್ ನಿರ್ವಹಣೆ / Site Management")
            setDisplayHomeAsUpEnabled(true)
        }
    }
    
    private fun setupRecyclerView() {
        siteAdapter = SiteAdapter(
            onOpenSite = { site ->
                siteViewModel.selectSite(site)
                finish() // Return to main activity with selected site
            },
            onMarkComplete = { site ->
                confirmMarkComplete(site)
            },
            onDeleteSite = { site ->
                confirmDeleteSite(site)
            }
        )
        
        binding.recyclerViewSites.apply {
            layoutManager = LinearLayoutManager(this@SiteManagementActivity)
            adapter = siteAdapter
        }
    }
    
    private fun setupClickListeners() {
        binding.fabAddSite.setOnClickListener {
            showAddSiteDialog()
        }
    }
    
    private fun showAddSiteDialog() {
        val dialog = AddSiteDialog()
        dialog.setOnSiteAddedListener { site ->
            siteViewModel.createSite(site)
        }
        dialog.show(supportFragmentManager, "AddSiteDialog")
    }
    
    private fun confirmMarkComplete(site: Site) {
        MaterialAlertDialogBuilder(this)
            .setTitle("MARK COMPLETE")
            .setMessage("ಈ ಸೈಟ್ ಅನ್ನು ಪೂರ್ಣಗೊಳಿಸಿದಂತೆ ಗುರುತಿಸಬೇಕೇ?\nMark '${site.name}' as complete?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                siteViewModel.markComplete(site.id)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun confirmDeleteSite(site: Site) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete))
            .setMessage("ಈ ಸೈಟ್ ಅನ್ನು ಅಳಿಸಬೇಕೇ? ಎಲ್ಲಾ ಡೇಟಾ ಕಳೆಯುತ್ತದೆ!\nDelete '${site.name}'? All data will be lost!")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                siteViewModel.deleteSite(site.id)
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }
    
    private fun observeViewModel() {
        siteViewModel.allSites.observe(this) { sites ->
            siteAdapter.submitList(sites)
            binding.textNoSites.showIf(sites.isEmpty())
        }
        
        siteViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.show()
            } else {
                binding.progressBar.hide()
            }
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

// Extension functions for view visibility
private fun android.view.View.show() {
    visibility = android.view.View.VISIBLE
}

private fun android.view.View.hide() {
    visibility = android.view.View.GONE
}

private fun android.view.View.showIf(condition: Boolean) {
    visibility = if (condition) android.view.View.VISIBLE else android.view.View.GONE
}
