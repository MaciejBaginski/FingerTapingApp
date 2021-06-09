package com.example.fingertapingapp

class Validator {
    companion object {
        fun validateUserLoginData(name: String, surname: String, ageAsString: String) {
            if(anyFieldIsEmpty(name, surname, ageAsString)) {
                throw ValidationException("All field must be correctly filled.")
            }

            val age = ageAsString.toIntOrNull()
            if(ageIsInValidRange(age)) {
                throw ValidationException("Age must be number in range 1 - 199")
            }
        }

        private fun anyFieldIsEmpty(name: String, surname: String, age: String): Boolean {
            return name.isBlank() || surname.isBlank() || age.isBlank()
        }

        private fun ageIsInValidRange(age: Int?): Boolean {
            return age == null || age < 1 || age > 199
        }
    }
}