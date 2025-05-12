Chelete Tracker - Expense Management App


This part 2 

The App Protype Development

Which used Activties for the layout then used RoomDb for database

is a personal finance application that helps users track their expenses, manage budgets, and organize spending categories. Built with modern Android development practices including Room Database, ViewModel, LiveData, and ViewBinding.

 Features

Budget Management:
  - Set monthly budget goals (min/max amounts)
  - Track spending progress with visual indicators
  - View current spending percentage of budget

  Expense Tracking:
  - Record and categorize expenses
  - View expense history in a list
  - Filter expenses by date range

  Category Management:
  - Create custom spending categories
  - Delete unused categories
  - View spending by category

  User System:
  - Secure user authentication
  - Personalized data for each user


 Key Components

1. Database Layer:
   - Room Database with entities: `User`, `Category`, `Expense`, `BudgetGoal`
   - DAO interfaces for each entity with suspend functions

2. Repository Layer:
   - `BudgetGoalRepository`, `ExpenseRepository`, `CategoryRepository`, `UserRepository`
   - Abstracts data sources from ViewModels
   - Handles all database operations

3. ViewModel Layer:
   - `BudgetGoalViewModel`, `ExpenseViewModel`, `CategoryViewModel`, `UserViewModel`
   - Provides data to UI components
   - Survives configuration changes
   - Uses coroutines for background operations

4. UI Layer:
   - Fragments: `BudgetFragment`, `ExpensesFragment`, `CategoriesFragment`
   - ViewBinding for type-safe view access
   - RecyclerView with custom adapters
   - AlertDialogs for user input


Technologies Used
Android Studio
Kotlin
RoomDb

References
This project adapts some code from this source:
Author: Mkr Developer
Source:
