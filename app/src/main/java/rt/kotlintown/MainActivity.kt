@file:Suppress("UNUSED_VARIABLE", "unused")

package rt.kotlintown

import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.annotation.StyleRes
import android.support.annotation.StyleableRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintSet.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import rt.kotlintown.dsl.constrain
import rt.kotlintown.dsl.guideline
import rt.kotlintown.dsl.update
import rt.kotlintown.recycler.AnAwesomeAdapter

class MainActivity : AppCompatActivity() {

  private var alternate = false
  private val previousScrollPosition = 0
  private val data: List<String> = emptyList()

  private val messageTextView: TextView by lazy {
    findViewById<TextView>(R.id.result)
  }

  private val updateButton: Button by lazy {
    findViewById<Button>(R.id.change_button)
  }

  private val photoView: ImageView by lazy {
    findViewById<ImageView>(R.id.image)
  }

  private val recyclerView: RecyclerView by lazy {
    findViewById<RecyclerView>(R.id.recycler_view)
  }

  private val constraintLayout: ConstraintLayout by lazy {
    findViewById<ConstraintLayout>(R.id.constraint_layout)
  }

  private val constraintSetOriginal by lazy {
    ConstraintSet().apply {
      clone(this@MainActivity, R.layout.activity_main_01)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_01)
    findViewById<Button>(R.id.change_button).setOnClickListener {
      if (alternate) {
        constraintSetOriginal.applyTo(constraintLayout)
      } else {
//        updateConstraint()
        updateConstraintsViaDsl()
      }
      alternate = !alternate
    }
  }








  //
  //   _                     _         _
  //  | |                   | |       | |
  //  | |     __ _ _ __ ___ | |__   __| | __ _ ___
  //  | |    / _` | '_ ` _ \| '_ \ / _` |/ _` / __|
  //  | |___| (_| | | | | | | |_) | (_| | (_| \__ \
  //  |______\__,_|_| |_| |_|_.__/ \__,_|\__,_|___/
  //
  //            _ _   _                         _
  //           (_) | | |                       (_)
  //  __      ___| |_| |__    _ __ ___  ___ ___ ___   _____ _ __ ___
  //  \ \ /\ / / | __| '_ \  | '__/ _ \/ __/ _ \ \ \ / / _ \ '__/ __|
  //   \ V  V /| | |_| | | | | | |  __/ (_|  __/ |\ V /  __/ |  \__ \
  //    \_/\_/ |_|\__|_| |_| |_|  \___|\___\___|_| \_/ \___|_|  |___/
  //
  //
  //
  // - Specify a _receiver object_ for the lambda.
  // - Call methods inside of the lambda on the _receiver object_
  //   without qualifiers.
  // - It becomes the "this" inside that lambda
  //


  //
  // BEFORE
  //

  fun updateSomeInterfaceStuff(listUpdate: (recyclerView: RecyclerView) -> Unit) {
    listUpdate(recyclerView)

    updateButton.visibility = View.VISIBLE
  }

  val basicRecyclerViewSetup: (RecyclerView) -> Unit = { recyclerView ->
    recyclerView.adapter = AnAwesomeAdapter()
    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity,
        DividerItemDecoration.HORIZONTAL))
  }

  //
  // AFTER
  //

  fun updateSomeInterfaceStuffWithReceivers(listUpdate: RecyclerView?.() -> Unit) {
    recyclerView.listUpdate()

    updateButton.visibility = View.VISIBLE
  }

  val basicRecyclerViewSetupWithReceivers: RecyclerView?.() -> Unit = setup@ {
    if (this == null) {
      return@setup
    }
    adapter = AnAwesomeAdapter()
    layoutManager = LinearLayoutManager(this@MainActivity)
    addItemDecoration(DividerItemDecoration(this@MainActivity,
        DividerItemDecoration.HORIZONTAL))
  }










  //   ______      _                 _
  //  |  ____|    | |               (_)
  //  | |__  __  _| |_ ___ _ __  ___ _  ___  _ __
  //  |  __| \ \/ / __/ _ \ '_ \/ __| |/ _ \| '_ \
  //  | |____ >  <| ||  __/ | | \__ \ | (_) | | | |
  //  |______/_/\_\\__\___|_| |_|___/_|\___/|_| |_|
  //
  //   ______                _   _
  //  |  ____|              | | (_)
  //  | |__ _   _ _ __   ___| |_ _  ___  _ __  ___
  //  |  __| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
  //  | |  | |_| | | | | (__| |_| | (_) | | | \__ \
  //  |_|   \__,_|_| |_|\___|\__|_|\___/|_| |_|___/
  //
  //
  //
  // KOTLIN IN ACTION:
  //   Lambdas are to lambdas with receivers
  //   as functions are to extension functions…
  //
  // - Take a function, select a parameter and make it the receiver.
  // - Call methods inside the function on that object without qualifiers.
  // - It becomes the _this_.

  // How we might right this method in a Java-esque way
  fun isNullOrEmptyObject(json: String?) = json?.equals("{}")

  // Turning the above function into an extension method.
  fun String?.isNullOrEmptyObject() = this == null || this == "{}"


  fun handleJson(json: String?) {

    isNullOrEmptyObject(json)

    // VS

    json.isNullOrEmptyObject()
  }

  // Are we actually extending the base class? Newp.

  /*

    Here's the bytecode…

    @Nullable
    public final Boolean isNullOrEmptyObject(@Nullable String $receiver) {
        return $receiver != null ? Boolean.valueOf($receiver.equals("{}")) : null;
    }

  */










  //
  // Lambdas with receivers… Extension methods…
  //
  // Huh. What are they good for?
  // Absolutely tons! Listen to me…
  //

  // - Reduce ceremony
  // - Prevent bugs

  //
  //
  // Execute Around Method (EAM)
  // wiki.c2.com/?ExecuteAroundMethod
  //
  // Design Patterns in the Light of Lambda Expressions <-- Watch this talk!
  // Venkat Subramaniam
  // YouTube | youtu.be/e4MT_OguDKg
  //
  //

  fun updateFromStyle() {
    val array: TypedArray = obtainStyledAttributes(R.style.TheMostBeautifulStyleEver,
        R.styleable.MyStyleable)
    val background = array.getDrawable(R.styleable.MyStyleable_android_background)
    val textBackground = array.getColor(R.styleable.MyStyleable_colorPrimary, Color.WHITE)
    val textColor = array.getColor(R.styleable.MyStyleable_colorAccent, Color.BLACK)
    array.recycle()
  }

  //
  // Now using EAM
  //

  fun updateMoreStylishly(@StyleRes styleId: Int, @StyleableRes styleableId: IntArray,
                          block: TypedArray.() -> Unit) {
    val array: TypedArray = obtainStyledAttributes(R.style.TheMostBeautifulStyleEver,
        R.styleable.MyStyleable)
    array.block()
    array.recycle()
  }

  fun updateSomeUiStuff() {
    updateMoreStylishly(R.style.TheMostBeautifulStyleEver, R.styleable.MyStyleable) {
      val background = getDrawable(R.styleable.MyStyleable_android_background)
      val textBackgroundColor = getColor(R.styleable.MyStyleable_colorPrimary, Color.WHITE)
      val textColor = getColor(R.styleable.MyStyleable_colorAccent, Color.BLACK)
    }
  }



  // And now for something completely different…

  //
  //   _____                        _          _____                 _  __ _
  //  |  __ \                      (_)        / ____|               (_)/ _(_)
  //  | |  | | ___  _ __ ___   __ _ _ _ __   | (___  _ __   ___  ___ _| |_ _  ___
  //  | |  | |/ _ \| '_ ` _ \ / _` | | '_ \   \___ \| '_ \ / _ \/ __| |  _| |/ __|
  //  | |__| | (_) | | | | | | (_| | | | | |  ____) | |_) |  __/ (__| | | | | (__
  //  |_____/ \___/|_| |_| |_|\__,_|_|_| |_| |_____/| .__/ \___|\___|_|_| |_|\___|
  //                                                | |
  //                                                |_|
  //   _                                                       _______   _____ _        __
  //  | |                                                     / /  __ \ / ____| |       \ \
  //  | |     __ _ _ __   __ _ _   _  __ _  __ _  ___  ___   | || |  | | (___ | |     ___| |
  //  | |    / _` | '_ \ / _` | | | |/ _` |/ _` |/ _ \/ __|  | || |  | |\___ \| |    / __| |
  //  | |___| (_| | | | | (_| | |_| | (_| | (_| |  __/\__ \  | || |__| |____) | |____\__ \ |
  //  |______\__,_|_| |_|\__, |\__,_|\__,_|\__, |\___||___/  | ||_____/|_____/|______|___/ |
  //                     __/ |             __/ |              \_\                       /_/
  //                    |___/             |___/
  //

  // - Small languages designed for specific problem spaces.
  // - Specialized languages.
  // - Often written in general purpose languages (GPLs)

  // Example: Gradle (written in Groovy)
  //
  // android {
  //     compileSdkVersion 26
  //     buildToolsVersion "26.0.2"
  //     defaultConfig {
  //         applicationId "com.myapp"
  //

  //
  // And you can create your own in Kotlin!
  // …with lambdas with receivers! And extensions!
  //


  //
  // Example: ConstraintLayout + ConstraintSet, full + rich API but can be a lot…
  //

  /**
   * An extension method to make it a little easier to grab dimensions.
   *
   * Basically like [android.content.Context.getString] but for dimensions.
   */
  fun AppCompatActivity.getDimenSize(@DimenRes resId: Int) =
      resources.getDimensionPixelSize(resId)

  //region Version 1: Straight up API

  fun updateConstraint() {
    val constraintSet = ConstraintSet()
    constraintSet.clone(constraintLayout)

    constraintSet.create(R.id.content_start, VERTICAL_GUIDELINE)
    constraintSet.setGuidelineBegin(R.id.content_start,
        getDimenSize(R.dimen.keyline_start))
    constraintSet.create(R.id.content_top, HORIZONTAL_GUIDELINE)
    constraintSet.setGuidelineBegin(R.id.content_top,
        getDimenSize(R.dimen.content_padding_top))

    constraintSet.clear(R.id.image)
    constraintSet.constrainWidth(R.id.image, MATCH_CONSTRAINT_SPREAD)
    constraintSet.constrainHeight(R.id.image, MATCH_CONSTRAINT_SPREAD)
    constraintSet.connect(R.id.image, START, PARENT_ID, START)
    constraintSet.connect(R.id.image, TOP, PARENT_ID, TOP)
    constraintSet.connect(R.id.image, END, PARENT_ID, END)
    constraintSet.connect(R.id.image, BOTTOM, PARENT_ID, BOTTOM)

    constraintSet.clear(R.id.title)
    constraintSet.constrainWidth(R.id.title, WRAP_CONTENT)
    constraintSet.constrainHeight(R.id.title, WRAP_CONTENT)
    constraintSet.connect(R.id.title, END, R.id.content_start, END)
    constraintSet.connect(R.id.title, TOP, R.id.content_top, TOP)

    constraintSet.clear(R.id.artist)
    constraintSet.constrainWidth(R.id.artist, WRAP_CONTENT)
    constraintSet.constrainHeight(R.id.artist, WRAP_CONTENT)
    constraintSet.connect(R.id.artist, START, R.id.title, END)
    constraintSet.connect(R.id.artist, TOP, R.id.title, BOTTOM)

    constraintSet.applyTo(constraintLayout)
  }

  //endregion

  //region Version 2: Extension + lambda with receiver

  fun updateConstraintViaLambdasWithReceivers() {

    constraintLayout.update {
      create(R.id.content_start, VERTICAL_GUIDELINE)
      setGuidelineBegin(R.id.content_start, getDimenSize(R.dimen.keyline_start))
      create(R.id.content_top, HORIZONTAL_GUIDELINE)
      setGuidelineBegin(R.id.content_top, getDimenSize(R.dimen.content_padding_top))

      clear(R.id.image)
      constrainWidth(R.id.image, MATCH_CONSTRAINT_SPREAD)
      constrainHeight(R.id.image, MATCH_CONSTRAINT_SPREAD)
      connect(R.id.image, START, PARENT_ID, START)
      connect(R.id.image, TOP, PARENT_ID, TOP)
      connect(R.id.image, END, PARENT_ID, END)
      connect(R.id.image, BOTTOM, PARENT_ID, BOTTOM)

      clear(R.id.title)
      constrainWidth(R.id.title, MATCH_CONSTRAINT_SPREAD)
      constrainHeight(R.id.title, MATCH_CONSTRAINT_SPREAD)
      connect(R.id.title, END, R.id.content_start, END)
      connect(R.id.title, TOP, R.id.content_top, TOP)

      clear(R.id.artist)
      constrainWidth(R.id.artist, MATCH_CONSTRAINT_SPREAD)
      constrainHeight(R.id.artist, MATCH_CONSTRAINT_SPREAD)
      connect(R.id.artist, START, R.id.title, END)
      connect(R.id.artist, TOP, R.id.title, BOTTOM)
    }
  }

  //endregion


  //
  // Hmm… can we turn these API calls into something more structure
  // like an actual layout?
  //

  //region Going nuts with a DSL-esque

  fun updateConstraintsViaDsl() {

    constraintLayout.update {

      guideline(R.id.content_start, VERTICAL_GUIDELINE, LEFT, getDimenSize(R.dimen.keyline_start))
      guideline(R.id.content_top, HORIZONTAL_GUIDELINE, TOP, getDimenSize(R.dimen.content_padding_top))

      constrain(R.id.image) {
        width = MATCH_CONSTRAINT_SPREAD
        height = MATCH_CONSTRAINT_SPREAD
        start = START of PARENT_ID
        top = TOP of PARENT_ID
        end = END of PARENT_ID
        bottom = BOTTOM of PARENT_ID
      }

      constrain(R.id.title) {
        width = WRAP_CONTENT
        height = WRAP_CONTENT
        end = END of R.id.content_start
        top = TOP of R.id.content_top
      }

      constrain(R.id.artist) {
        width = WRAP_CONTENT
        height = WRAP_CONTENT
        start = END of R.id.title
        top = BOTTOM of R.id.title
      }
    }
  }

  //endregion









  
  //
  //   _______ _            _____                 _   _
  //  |__   __| |          |  __ \               | | | |
  //     | |  | |__   ___  | |__) |___   __ _  __| | | |_ ___
  //     | |  | '_ \ / _ \ |  _  // _ \ / _` |/ _` | | __/ _ \
  //     | |  | | | |  __/ | | \ \ (_) | (_| | (_| | | || (_) |
  //     |_|  |_| |_|\___| |_|  \_\___/ \__,_|\__,_|  \__\___/
  //
  //    _  __     _   _ _       _
  //   | |/ /    | | | (_)     | |
  //   | ' / ___ | |_| |_ _ __ | |_ _____      ___ __
  //   |  < / _ \| __| | | '_ \| __/ _ \ \ /\ / / '_ \
  //   | . \ (_) | |_| | | | | | || (_) \ V  V /| | | |
  //   |_|\_\___/ \__|_|_|_| |_|\__\___/ \_/\_/ |_| |_|
  //
  //
  //
  // Many of the things that you love about Kotlin are
  // made leveraging these things that we've talked about:
  //
  // Generics
  // Type system
  // Lambdas with receivers
  // Extension functions
  //

  fun stdlibLove() {
    startActivity(Intent().apply {
      action = Intent.ACTION_VIEW
      putExtra("THIS", "THAT")
    })

    "".isNullOrEmpty()
  }

}
