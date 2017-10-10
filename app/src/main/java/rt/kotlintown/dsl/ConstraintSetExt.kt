@file:Suppress("unused")

package rt.kotlintown.dsl

import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintSet.*

//region // Using lambdas with receivers…

/**
 * Extension method that takes a lambda with receiver as a parameter.
 *
 * Use it to do EAM pattern to take away ceremony of managing and applying the [ConstraintSet].
 */
fun ConstraintLayout.update(init: ConstraintSet.() -> Unit) {
  val set = ConstraintSet()
  set.clone(this)
  set.init()
  set.applyTo(this)
}


//endregion

//region // Going full DSL…

/**
 * Extension method for creating a guideline with a single call.
 *
 * Throws in a litle bit of error-checking.
 */
fun ConstraintSet.guideline(@IdRes guidelineId: Int, orientation: Int, side: Int, margin: Int) {
  if (side < LEFT || side > BOTTOM) throw IllegalArgumentException("Invalid side: $side")

  if (((side == LEFT || side == RIGHT) && orientation == HORIZONTAL_GUIDELINE) ||
      ((side == TOP || side == BOTTOM) && orientation == VERTICAL_GUIDELINE))
    throw IllegalArgumentException("Mismatched guideline orientation and selected size")

  // Actually create the guideline.
  create(guidelineId, orientation)
  when (side) {
    LEFT, TOP -> setGuidelineBegin(guidelineId, margin)
    RIGHT, BOTTOM -> setGuidelineEnd(guidelineId, margin)
  }
}

/**
 * Extension method for constraining view defined by [viewId].
 *
 * Constraints are defined in [block] using a [Constraints] builder object. The builder object is
 * partially hidden by the lambda with receiver syntax: whoever calls this method does not have
 * to instantiate [Constraints] instances directly but just calls initialization methods on it.
 */
fun ConstraintSet.constrain(@IdRes viewId: Int, block: Constraints.() -> Unit) {
  Constraints().run {
    block()
    clear(viewId)
    constrainWidth(viewId, width)
    constrainHeight(viewId, height)
    addEdgeConstraint(viewId, START, start)
    addEdgeConstraint(viewId, END, end)
    addEdgeConstraint(viewId, TOP, top)
    addEdgeConstraint(viewId, BOTTOM, bottom)
  }
}

/**
 * Convert a pair representing the target of an edge constraint to a [ConstraintSet.connect] call.
 *
 * @param sourceId ID of source view (view being constrained)
 * @param sourceSide The side ([START], [END], [LEFT], or [RIGHT]) of the view defined by [sourceId]
 * which is being constrained.
 * @param target a pair representing the constraint target: (target view ID, target side)
 */
fun ConstraintSet.addEdgeConstraint(@IdRes sourceId: Int, sourceSide: Int,
                                    target: Pair<Int, Int>?) {
  if (target == null) return
  connect(sourceId, sourceSide, target.first, target.second)
}


/**
 * Builder object that allows us to provide a more concise syntax for setting up a constraint.
 *
 * Holds the initialization values for a view's constraints and presents the caller a nice syntax.
 * Then later the properties of this object are passed to the write API calls to actually create
 * the view's constraints.
 */
class Constraints {

  //
  // NOTE: This is not an exhaustive example of constraints you can define in a ConstraintSet but
  // just a subset as an example.
  //

  var width: Int = WRAP_CONTENT
  var height: Int = WRAP_CONTENT

  var start: Pair<Int, Int>? = null
  var end: Pair<Int, Int>? = null
  var top: Pair<Int, Int>? = null
  var bottom: Pair<Int, Int>? = null


  /**
   * Create a pair of target view ID to target view Side, e.g., (R.id.my_view, START)
   */
  infix fun Int.of(@IdRes targetIdRes: Int) = targetIdRes to this
}

// endregion
