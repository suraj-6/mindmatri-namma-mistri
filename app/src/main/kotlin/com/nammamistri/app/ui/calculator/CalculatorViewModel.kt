package com.nammamistri.app.ui.calculator

import androidx.lifecycle.*
import com.nammamistri.app.data.db.CalculationDao
import com.nammamistri.app.data.model.Calculation
import com.nammamistri.app.data.model.CalculationType
import com.nammamistri.app.data.repository.CalculationRepository
import com.nammamistri.app.utils.FormulaHelper
import com.nammamistri.app.utils.format
import kotlinx.coroutines.launch

/**
 * ViewModel for Calculator Fragment
 */
class CalculatorViewModel(
    private val calculationDao: CalculationDao
) : ViewModel() {
    
    private val repository = CalculationRepository(calculationDao)
    
    val allCalculations: LiveData<List<Calculation>> = calculationDao.getAllCalculationsLiveData()
    
    private val _calculationType = MutableLiveData(CalculationType.BRICK_WORK)
    val calculationType: LiveData<CalculationType> = _calculationType
    
    private val _calculationResult = MutableLiveData<String?>()
    val calculationResult: LiveData<String?> = _calculationResult
    
    private var lastCalculation: Calculation? = null
    private var lastLength: Double = 0.0
    private var lastWidth: Double = 0.0
    private var lastHeight: Double = 0.0
    
    fun setCalculationType(type: CalculationType) {
        _calculationType.value = type
        clearResult()
    }
    
    fun calculate(length: Double, width: Double, height: Double) {
        lastLength = length
        lastWidth = width
        lastHeight = height
        
        val type = _calculationType.value ?: CalculationType.BRICK_WORK
        val area = FormulaHelper.calculateArea(length, width)
        
        val result = when (type) {
            CalculationType.BRICK_WORK -> calculateBrickwork(length, width)
            CalculationType.PLASTERING -> calculatePlastering(area)
            CalculationType.CONCRETE -> calculateConcrete(length, width, height)
            CalculationType.PAINTING -> calculatePainting(area)
            CalculationType.TILE_WORK -> calculateTiles(area)
            CalculationType.CEMENT_SAND -> calculateCementSand(area)
            CalculationType.FLOORING -> calculateFlooring(area)
            else -> "Calculation not available for this type"
        }
        
        _calculationResult.value = result
    }
    
    private fun calculateBrickwork(length: Double, height: Double): String {
        val result = FormulaHelper.calculateBricks(length, height)
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.BRICK_WORK,
            length = length,
            width = height,
            area = result.areaSqft,
            quantity = result.numberOfBricks.toDouble(),
            unit = "Bricks"
        )
        
        return """
            🧱 BRICKWORK CALCULATION
            ━━━━━━━━━━━━━━━━━━━━━━━
            Wall Area: ${result.areaSqft.format(2)} sqft
            
            Materials Required:
            • Bricks: ${result.numberOfBricks} nos
              (incl. 5% wastage)
            • Mortar: ${result.mortarVolumeCft.format(2)} cft
            
            For mortar (1:6 ratio):
            • Cement: ${(result.mortarVolumeCft * 0.22).format(1)} bags
            • Sand: ${(result.mortarVolumeCft * 1.1).format(1)} cft
        """.trimIndent()
    }
    
    private fun calculatePlastering(area: Double): String {
        val result12mm = FormulaHelper.calculatePlastering(area, 12)
        val result20mm = FormulaHelper.calculatePlastering(area, 20)
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.PLASTERING,
            length = lastLength,
            width = lastWidth,
            area = area,
            quantity = result12mm.cementBags,
            unit = "Bags Cement"
        )
        
        return """
            🏗️ PLASTERING CALCULATION
            ━━━━━━━━━━━━━━━━━━━━━━━━
            Area: ${area.format(2)} sqft
            
            For 12mm Plaster (1:6):
            • Cement: ${result12mm.cementBags.format(1)} bags
            • Sand: ${result12mm.sandCft.format(1)} cft
            
            For 20mm Plaster (1:4):
            • Cement: ${result20mm.cementBags.format(1)} bags
            • Sand: ${result20mm.sandCft.format(1)} cft
        """.trimIndent()
    }
    
    private fun calculateConcrete(length: Double, width: Double, height: Double): String {
        val volume = FormulaHelper.calculateVolume(length, width, height)
        val m20 = FormulaHelper.calculateConcrete(volume, "M20")
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.CONCRETE,
            length = length,
            width = width,
            height = height,
            area = length * width,
            quantity = volume,
            unit = "Cft"
        )
        
        return """
            🏗️ CONCRETE CALCULATION (M20)
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━
            Volume: ${volume.format(2)} cft
                    (${FormulaHelper.cftToCum(volume).format(3)} cum)
            
            Materials Required (1:1.5:3):
            • Cement: ${m20.cementBags.format(1)} bags
            • Sand: ${m20.sandCft.format(1)} cft
            • Aggregate (20mm): ${m20.aggregateCft.format(1)} cft
            
            Steel (approx 1%): ${(volume * 0.01 * 78.5).format(1)} kg
        """.trimIndent()
    }
    
    private fun calculatePainting(area: Double): String {
        val paint = FormulaHelper.calculatePaint(area)
        val primer = FormulaHelper.calculatePaint(area, 1, 120.0)
        val putty = area / 20 // 20 sqft per kg
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.PAINTING,
            length = lastLength,
            width = lastWidth,
            area = area,
            quantity = paint,
            unit = "Liters"
        )
        
        return """
            🎨 PAINTING CALCULATION
            ━━━━━━━━━━━━━━━━━━━━━━━
            Area: ${area.format(2)} sqft
            
            Materials Required:
            • Primer: ${primer.format(1)} liters (1 coat)
            • Putty: ${putty.format(1)} kg
            • Paint: ${paint.format(1)} liters (2 coats)
            
            Coverage assumed:
            • Primer: 120 sqft/liter
            • Putty: 20 sqft/kg
            • Paint: 100 sqft/liter
        """.trimIndent()
    }
    
    private fun calculateTiles(area: Double): String {
        // 2x2 feet tiles
        val result2x2 = FormulaHelper.calculateTiles(area, 24.0, 24.0)
        // 2x1 feet tiles
        val result2x1 = FormulaHelper.calculateTiles(area, 24.0, 12.0)
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.TILE_WORK,
            length = lastLength,
            width = lastWidth,
            area = area,
            quantity = result2x2.numberOfTiles.toDouble(),
            unit = "Tiles (2x2)"
        )
        
        return """
            🔲 TILE CALCULATION
            ━━━━━━━━━━━━━━━━━━━
            Area: ${area.format(2)} sqft
            
            For 2ft x 2ft tiles:
            • Tiles: ${result2x2.numberOfTiles} nos
            • Boxes: ${result2x2.numberOfBoxes} (4 tiles/box)
            
            For 2ft x 1ft tiles:
            • Tiles: ${result2x1.numberOfTiles} nos
            • Boxes: ${result2x1.numberOfBoxes} (4 tiles/box)
            
            (Includes 10% wastage)
        """.trimIndent()
    }
    
    private fun calculateCementSand(area: Double): String {
        // Assuming 1:4 ratio flooring
        val flooring = FormulaHelper.calculatePlastering(area, 40, Pair(1, 4))
        
        lastCalculation = Calculation(
            projectName = "",
            calculationType = CalculationType.CEMENT_SAND,
            length = lastLength,
            width = lastWidth,
            area = area,
            quantity = flooring.cementBags,
            unit = "Bags Cement"
        )
        
        return """
            📦 CEMENT-SAND CALCULATION
            ━━━━━━━━━━━━━━━━━━━━━━━━━
            Area: ${area.format(2)} sqft
            
            For 40mm bedding (1:4):
            • Cement: ${flooring.cementBags.format(1)} bags
            • Sand: ${flooring.sandCft.format(1)} cft
        """.trimIndent()
    }
    
    private fun calculateFlooring(area: Double): String {
        val result = FormulaHelper.calculatePlastering(area, 50, Pair(1, 4))
        
        return """
            🏠 FLOORING CALCULATION
            ━━━━━━━━━━━━━━━━━━━━━━━
            Area: ${area.format(2)} sqft
            
            Base (50mm thick, 1:4):
            • Cement: ${result.cementBags.format(1)} bags
            • Sand: ${result.sandCft.format(1)} cft
        """.trimIndent()
    }
    
    fun saveCalculation(projectName: String) {
        lastCalculation?.let { calc ->
            val calculationToSave = calc.copy(
                projectName = projectName,
                updatedAt = System.currentTimeMillis()
            )
            viewModelScope.launch {
                repository.insert(calculationToSave)
            }
        }
    }
    
    fun deleteCalculation(calculation: Calculation) {
        viewModelScope.launch {
            repository.delete(calculation)
        }
    }
    
    fun clearResult() {
        _calculationResult.value = null
        lastCalculation = null
    }
}

/**
 * ViewModelFactory for CalculatorViewModel
 */
class CalculatorViewModelFactory(
    private val calculationDao: CalculationDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(calculationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
