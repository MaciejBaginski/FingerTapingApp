package com.example.fingertapingapp.validator

class Validator {
    companion object {
        fun validateUserLoginData(name: String, surname: String, ageAsString: String) {
            if (anyFieldIsEmpty(name, surname, ageAsString)) {
                throw ValidationException("All field must be correctly filled.")
            }

            val age = ageAsString.toIntOrNull()
            if (ageIsInValidRange(age)) {
                throw ValidationException("Age must be number in range 1 - 199")
            }
        }

        private fun anyFieldIsEmpty(vararg field: String): Boolean {
            for (content: String in field) {
                if (content.isBlank()) {
                    return true
                }
            }
            return false
        }

        private fun ageIsInValidRange(age: Int?): Boolean {
            return age == null || age < 1 || age > 199
        }

        fun validateCaretakerLoginData(login: String, password: String) {
            if (anyFieldIsEmpty(login, password)) {
                throw ValidationException("All field must be correctly filled.")
            }
        }
    }
}