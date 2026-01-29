# Weight API Implementation Plan

## Overview
This document outlines the implementation of a new API endpoint to handle weight calculations in kilograms (KG) and grams.

## Requirements
Create a new API endpoint that:
- Accepts weight input in KG and grams
- Converts and normalizes the total weight
- Returns the result in both input values and normalized output (KG and grams separately)

## Technical Specification

### API Endpoint
- **URL**: `/api/calculator/addWeight`
- **Method**: GET
- **Parameters**:
  - `kg` (double): Weight in kilograms
  - `grams` (double): Weight in grams
- **Response**: WeightResponse JSON object

### Response Model
```json
{
  "kilograms": <input_kg>,
  "grams": <input_grams>,
  "totalKilograms": <normalized_kg>,
  "totalGrams": <normalized_grams>,
  "operation": "weight addition",
  "error": null
}
```

### Business Logic
The `addWeight` method in `CalculatorService`:
1. Converts kilograms to grams (kg * 1000)
2. Adds the input grams to the converted value
3. Normalizes the result:
   - Total kilograms = floor(total_grams / 1000)
   - Remaining grams = total_grams % 1000

### Example Calculations

#### Example 1: Standard Input
- Input: kg=2, grams=500
- Calculation: (2 * 1000) + 500 = 2500 grams
- Output: totalKilograms=2, totalGrams=500

#### Example 2: Overflow Scenario
- Input: kg=1, grams=1500
- Calculation: (1 * 1000) + 1500 = 2500 grams
- Output: totalKilograms=2, totalGrams=500

#### Example 3: Zero Values
- Input: kg=0, grams=0
- Calculation: (0 * 1000) + 0 = 0 grams
- Output: totalKilograms=0, totalGrams=0

## Implementation Components

### 1. WeightResponse Class
- Data model for API response
- Contains input values (kg, grams)
- Contains normalized output (totalKilograms, totalGrams)
- Includes operation type and optional error message
- Location: `src/main/java/com/example/calculator/WeightResponse.java`

### 2. CalculatorService Enhancement
- Added `addWeight(double kilograms, double grams)` method
- Returns `WeightResult` inner class with normalized values
- Uses pure mathematical calculations without external dependencies
- Location: `src/main/java/com/example/calculator/CalculatorService.java`

### 3. CalculatorController Enhancement
- Added `/addWeight` GET endpoint
- Maps request parameters to service method
- Returns WeightResponse wrapped in ResponseEntity
- Location: `src/main/java/com/example/calculator/CalculatorController.java`

## Testing Strategy

### Unit Tests
1. **CalculatorServiceTest**:
   - Test standard weight addition
   - Test weight addition with overflow (grams > 1000)
   - Test zero values
   - Test with only grams input

2. **WeightResponseTest**:
   - Test constructor without error
   - Test constructor with error
   - Test all getters and setters

### Integration Tests
1. **CalculatorControllerTest**:
   - Test API endpoint with standard values
   - Test API endpoint with overflow scenario
   - Test API endpoint with zero values
   - Verify JSON response structure
   - Verify HTTP status codes

### Test Coverage Goals
- Maintain >80% overall code coverage
- 100% coverage for new weight-related methods
- All edge cases covered

## Deployment Considerations

### Dependencies
- No new dependencies required
- Uses existing Spring Boot framework
- Compatible with Java 17

### Backwards Compatibility
- New feature does not affect existing endpoints
- No breaking changes to existing APIs
- Existing tests remain unchanged

### Performance
- O(1) time complexity for weight calculation
- Minimal memory overhead
- No database or external service calls

## Documentation Updates

### README.md Updates
1. Added "Weight Addition" to features list
2. Added API endpoint documentation with examples
3. Updated project structure to include WeightResponse
4. Added curl example for the new endpoint

## Risk Assessment

### Low Risk
- Simple mathematical operations
- No external dependencies
- Well-tested implementation
- Follows existing patterns in the codebase

### Potential Issues
- None identified - straightforward feature addition

## Acceptance Criteria

- [x] API endpoint `/api/calculator/addWeight` is accessible
- [x] Accepts `kg` and `grams` query parameters
- [x] Returns correct normalized weight values
- [x] Handles overflow scenarios (grams > 1000)
- [x] Handles zero values correctly
- [x] Returns proper JSON response structure
- [x] Unit tests cover all scenarios
- [x] Integration tests verify API functionality
- [x] Documentation updated in README.md
- [x] Code coverage maintained above 80%

## Timeline
- **Planning**: ✓ Complete
- **Implementation**: ✓ Complete
- **Testing**: ✓ Complete
- **Documentation**: ✓ Complete
- **Code Review**: Pending
- **Deployment**: Ready

## Conclusion
The weight API has been successfully implemented following best practices and maintaining consistency with the existing codebase. The implementation is minimal, focused, and well-tested.
