package com.example.roomexample

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("RestrictedApi")
class AddUsersActivity : AppCompatActivity() {

    private lateinit var addNoteBackground: ConstraintLayout
    private lateinit var addNoteWindowBg: LinearLayout
    // Define an array of strings called "languages"
// The array contains a list of years from 2015 to 2023, including the string "Select"
    var languages = arrayOf("Select","2015", "2016", "2017", "2018", "2019", "2020","2021","2022","2023")
    var selected_gender = ""
    var selected_passyear = ""
    var starvalue = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        addNoteBackground = findViewById(R.id.add_note_background)
        addNoteWindowBg = findViewById(R.id.add_note_window_bg)

        setActivityStyle()
        // Getting the  extras from the Intent object to pre-populate fields in the UI
        val noteDateAdded = intent.getSerializableExtra("note_date_added") as? Date
        val noteTextToEdit = intent.getStringExtra("note_text")
        val emailTextToEdit = intent.getStringExtra("email_text")
        var genderTextToEdit = intent.getStringExtra("gender_text")
        val passyesrTextToEdit = intent.getStringExtra("passyear_text")
        val dobdateTextToEdit = intent.getStringExtra("dobdate_text")
        val datetimeTextToEdit = intent.getStringExtra("datetime_text")
        val startextToEdit = intent.getStringExtra("star_text")
        // Getting the  references to buttons and an ImageView in the UI
        val menButton = findViewById<Button>(R.id.maleButton)
        val womenButton = findViewById<Button>(R.id.femaleButton)
        val vectorImageView = findViewById<ImageView>(R.id.vector_image_view)

        val dobEditText = findViewById<TextView>(R.id.DOB_edittext)
        dobEditText.text = dobdateTextToEdit?:""

        val dateandtime_edittext = findViewById<TextView>(R.id.dateandtime_edittext)
        dateandtime_edittext.text = datetimeTextToEdit?: ""


        val addNoteText = findViewById<TextView>(R.id.add_note_text)
        addNoteText.text = noteTextToEdit ?: ""

        val addEmailText = findViewById<TextView>(R.id.add_email_text)
        addEmailText.text = emailTextToEdit ?: ""
        // Checking if the gender text received from intent is "Male"
        if(genderTextToEdit?.equals("Male") == true){
            // Setting the male vector image, background and text color of male button and background and text color of female button
            vectorImageView.setImageResource(R.drawable.ic_male)
            menButton.setBackgroundResource(R.color.Bluewhale)
            menButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            womenButton.setBackgroundResource(R.color.btnbgcolor)
            womenButton.setTextColor(ContextCompat.getColor(this, R.color.btntxtcolor))
            selected_gender =  "Male"  // set the selected gender as "Male"
        }else {  // If gender text is not "Male"
           // Set the female vector image, background and text color of female button and background and text color of male button
            vectorImageView.setImageResource(R.drawable.women)
            menButton.setBackgroundResource(R.color.btnbgcolor)
            menButton.setTextColor(ContextCompat.getColor(this, R.color.btntxtcolor))
            womenButton.setBackgroundResource(R.color.Bluewhale)
            womenButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            selected_gender =  "Female"
        }
        // Get reference to the "Add Note" button
        val addNoteButton = findViewById<Button>(R.id.add_note_button)
        // Set onClickListener to "Add Note" button
        addNoteButton.setOnClickListener {
            // Get the text entered by the user in the "Note" field
            val name = addNoteText.text.toString()
            // Get the text entered by the user in the "Email" field
            val email = addEmailText.text.toString()
            // Get the text entered by the user in the "Date of Birth" field
            val dob = dobEditText.text.toString()
            // Get the text entered by the user in the "Date and Time" field
            val datetime = dateandtime_edittext.text.toString()

        //Added validations
            if(name.isEmpty()) {
                Toast.makeText(this, "please add name", Toast.LENGTH_SHORT).show()

            }else  if(email.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "please add email", Toast.LENGTH_SHORT).show()
            }else if(selected_gender.isEmpty()||selected_gender.equals("")) {
                Toast.makeText(this, "please select gender", Toast.LENGTH_SHORT).show()

            }else if(selected_passyear.equals("Select")) {
                Toast.makeText(this, "please select passout year", Toast.LENGTH_SHORT).show()

            }else if(dob.isEmpty()) {
                Toast.makeText(this, "please select DOB", Toast.LENGTH_SHORT).show()

            }else if(datetime.isEmpty()) {
                Toast.makeText(this, "please select Date and Time", Toast.LENGTH_SHORT).show()

            }else if(starvalue.equals("0")) {
                Toast.makeText(this, "please rate your self", Toast.LENGTH_SHORT).show()

            }else{
                val data = Intent()
                data.putExtra("note_date_added", noteDateAdded)
                data.putExtra("note_text", addNoteText.text.toString())
                data.putExtra("email_text", addEmailText.text.toString())
                data.putExtra("gender_text", selected_gender)
                data.putExtra("passyear_text", selected_passyear)
                data.putExtra("dobdate_text", dobEditText.text.toString())
                data.putExtra("datetime_text", dateandtime_edittext.text.toString())
                data.putExtra("star_text", starvalue)
                setResult(Activity.RESULT_OK, data)
                // Close current window
                onBackPressed()
            }



        }
        // Get a reference to the spinner view in the layout
        val spinner = findViewById<Spinner>(R.id.spinner)
        // Check if the spinner view is not null
        if (spinner != null) {
            // Create an array adapter to populate the spinner with the language options
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            // Set the adapter on the spinner view to populate the language options
            spinner.adapter = adapter

            if (passyesrTextToEdit != null) {
                val spinnerPosition = adapter.getPosition(passyesrTextToEdit)
                spinner.setSelection(spinnerPosition)
            }
            // Set the on item selected listener for the spinner
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    selected_passyear = languages[position]

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        //click men icon
        menButton.setOnClickListener {
            vectorImageView.setImageResource(R.drawable.ic_male)
            menButton.setBackgroundResource(R.color.Bluewhale)
            menButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            womenButton.setBackgroundResource(R.color.btnbgcolor)
            womenButton.setTextColor(ContextCompat.getColor(this, R.color.btntxtcolor))
            selected_gender =  "Male"

        }
        //making women icon when click on women textview
        womenButton.setOnClickListener {
            vectorImageView.setImageResource(R.drawable.women)
            menButton.setBackgroundResource(R.color.btnbgcolor)
            menButton.setTextColor(ContextCompat.getColor(this, R.color.btntxtcolor))
            womenButton.setBackgroundResource(R.color.Bluewhale)
            womenButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            selected_gender =  "Female"
        }
        // Retrieve the rating value from some source
        val ratingValue = 3.4
        val star1 = findViewById<ImageView>(R.id.star1)
        val star2 = findViewById<ImageView>(R.id.star2)
        val star3 = findViewById<ImageView>(R.id.star3)
        val star4 = findViewById<ImageView>(R.id.star4)
        val star5 = findViewById<ImageView>(R.id.star5)
        // If the rating value to edit is equal to "1", set the corresponding star as filled and the rest as empty
        if(startextToEdit.equals("1")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_empty)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "1"
        }else  if(startextToEdit.equals("2")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "2"
        }else  if(startextToEdit.equals("3")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "3"
        } else  if(startextToEdit.equals("4")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "4"
        } else  if(startextToEdit.equals("5")){
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_filled)
            starvalue = "5"
        }

        star1.setOnClickListener {
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_empty)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "1"
        }
        star2.setOnClickListener {
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_empty)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "2"
        }
        star3.setOnClickListener {
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_empty)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "3"
        }
        star4.setOnClickListener {
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_empty)
            starvalue = "4"
        }
        star5.setOnClickListener {
            star1.setImageResource(R.drawable.ic_star_filled)
            star2.setImageResource(R.drawable.ic_star_filled)
            star3.setImageResource(R.drawable.ic_star_filled)
            star4.setImageResource(R.drawable.ic_star_filled)
            star5.setImageResource(R.drawable.ic_star_filled)
            starvalue = "5"
        }


        //written this code for selecting the dateofbirth when the user click on the image of calender

        dobEditText.setOnClickListener {
            val calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Calendar.getInstance()
            } else {
                TODO("VERSION.SDK_INT < N")
            }
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val formattedDate = "%02d-%02d-%d".format(dayOfMonth, month + 1, year)
                dobEditText.setText(formattedDate)
            }, year, month, dayOfMonth)

            datePickerDialog.show()
        }
        //here i have written this code for date and time selection when the user click on the image of the calender
        dateandtime_edittext.setOnClickListener {
            val currentDateAndTime = Calendar.getInstance()
            val currentYear = currentDateAndTime.get(Calendar.YEAR)
            val currentMonth = currentDateAndTime.get(Calendar.MONTH)
            val currentDayOfMonth = currentDateAndTime.get(Calendar.DAY_OF_MONTH)
            val currentHour = currentDateAndTime.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentDateAndTime.get(Calendar.MINUTE)
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val timePickerDialog = TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            val selectedDateTime = Calendar.getInstance()
                            selectedDateTime.set(year, month, dayOfMonth, hourOfDay, minute)
                            val dateTimeFormat =
                                SimpleDateFormat("dd-MM-yyyy | HH:mm", Locale.getDefault())
                            dateandtime_edittext.setText(dateTimeFormat.format(selectedDateTime.time))
                        },
                        currentHour,
                        currentMinute,
                        true
                    )
                    timePickerDialog.show()
                },
                currentYear,
                currentMonth,
                currentDayOfMonth
            )
            datePickerDialog.show()
        }
    }

  //This function sets the activity style by making the status bar and navigation bar transparent, and by animating the background color of the activity to appear gradually.
    //It also sets the alpha value of the addNoteWindowBg view to 0, animates it to 1, and sets listeners for the addNoteBackground and addNoteWindowBg views.

    private fun setActivityStyle() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.window.statusBarColor = Color.TRANSPARENT
        val winParams = this.window.attributes
        winParams.flags =
            winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        this.window.attributes = winParams

        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
        }
        colorAnimation.start()

        addNoteWindowBg.alpha = 0f
        addNoteWindowBg.animate().alpha(1f).setDuration(500)
            .setInterpolator(DecelerateInterpolator()).start()

        addNoteBackground.setOnClickListener { onBackPressed() }
        addNoteWindowBg.setOnClickListener { /* Prevent activity from closing when you tap on the popup's window background */ }
    }


    override fun onBackPressed() {
        // Set the background color and animation for the popup window
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->

        }

        addNoteWindowBg.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }
}