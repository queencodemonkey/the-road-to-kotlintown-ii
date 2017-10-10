# The Road to Kotlintown II

The `MainActivity` of this sample app contains the code from Huyen's portion of The Road to Kotlintown II: lambdas with receivers, extension functions, and DSLs.

When run, the main screen of the app shows the final DSL example with the `ConstraintLayout` and `ConstraintSet`. The initial layout of the image, title, and artist name is the original content view of the activity layout. Tapping the "Swap" button uses the DSL to change the layout using a `ConstraintSet`. Tapping "Swap" again returns the layout to the original constraints.