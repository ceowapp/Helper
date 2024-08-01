package com.aigeniusx.helpers

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit


class MainActivity_helper : AppCompatActivity() {
    private lateinit var dropdown: Spinner
    private lateinit var domainSpecificDropdown: Spinner
    private lateinit var submitButton: Button
    private lateinit var uploadButton: Button
    private lateinit var container: LinearLayout
    private val imageUris = mutableListOf<Uri>()
    private lateinit var imageViews: List<ImageView>
    private var currentImageViewIndex = 0
    private var selectedOptions = mutableListOf<String>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_01)

        dropdown = findViewById(R.id.dropdown)
        uploadButton = findViewById(R.id.upload_button)
        // Inflate the fragment's layout to access its views
        val fragmentView = layoutInflater.inflate(R.layout.fragment_layout_01, null)
        domainSpecificDropdown = fragmentView.findViewById(R.id.domain_specific_spinner)
        submitButton = findViewById(R.id.submit_button)
        // Get a reference to the AutoCompleteTextView in the layout
        val textView = findViewById<View>(R.id.autocomplete_country) as AutoCompleteTextView
        // Get the string array
        val countries = resources.getStringArray(R.array.countries_array)
        // Create the adapter and set it to the AutoCompleteTextView
        val adapter_txt = ArrayAdapter(this, R.layout.simple_list_item_1, countries)
        textView.setAdapter(adapter_txt)

        // Initialize your ImageViews
        imageViews = listOf(
            findViewById(R.id.image1),
            findViewById(R.id.image2),
            findViewById(R.id.image3)
        )



        // Set a click listener for the "Upload Image" button
        val uploadButton: Button = findViewById(R.id.upload_button)
        uploadButton.setOnClickListener {
            if (currentImageViewIndex < imageViews.size) {
                // Start an image picker intent
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, currentImageViewIndex)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode < imageViews.size) {
                // Get the selected image's URI
                val imageUri: Uri = data.data

                // Display the image in the appropriate ImageView
                imageViews[currentImageViewIndex].setImageURI(imageUri)

                // Save the URI in the list for future reference
                imageUris.add(imageUri)

                // Move to the next ImageView
                currentImageViewIndex++
            }
        }
    }


    private const val SPEECH_REQUEST_CODE = 0

    val recordButton: ImageButton = findViewById(R.id.record_button)
    recordButton.setOnClickListener {
        displaySpeechRecognizer()

    }


    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results[0]
                }
            // Do something with spokenText.
            data.paste(textView)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    AutoCompleteTextView.setOnEditorActionListener { textView, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Handle the action here
            val inputText = textView.text.toString()
            // Perform any desired operation with inputText
            Toast.makeText(this, "Action Send Clicked with input: $inputText", Toast.LENGTH_SHORT).show()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }


    // Define the list of items for the dropdown
    val items = listOf("TRIVIAL", "DOMAIN-SPECIFIC")

    // Create an ArrayAdapter to populate the dropdown with items
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)

    // Specify the layout for the dropdown's list of choices
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    // Attach the adapter to the dropdown
    dropdown.adapter = adapter

    // Set a listener to handle the first dropdown item selection
    dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val selectedValue = items[position]

            if (selectedOptions.size < 2) {
                selectedOptions.add(selectedValue)
            } else {
                Toast.makeText(applicationContext, "You can only select up to two options.", Toast.LENGTH_SHORT).show()
                dropdown.setSelection(0) // Reset the selection
            }

            // Update the domain-specific dropdown with options based on the selected value
            if (selectedValue == "DOMAIN-SPECIFIC") {
                val domainSpecificItems = listOf("Sub-Option 1", "Sub-Option 2", "Sub-Option 3")
                val domainSpecificAdapter = ArrayAdapter(this@MainActivity_01, android.R.layout.simple_spinner_item, domainSpecificItems)
                domainSpecificAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                domainSpecificDropdown.adapter = domainSpecificAdapter

                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add(R.id.fragment_container_view, FragmentActivity_needer())
                }

            } else {
                domainSpecificDropdown.adapter = null // Clear the options when not DOMAIN-SPECIFIC
                // Remove the fragment if necessary
                supportFragmentManager.beginTransaction().remove(FragmentActivity_needer()).commit()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Handle case where nothing is selected (optional)
        }
    }

    // Set a listener to handle the domain-specific dropdown item selection
    domainSpecificDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val selectedValue = domainSpecificDropdown.selectedItem.toString()
            Toast.makeText(applicationContext, "Selected Domain-Specific: $selectedValue", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Handle case where nothing is selected (optional)
        }
    }

    // Set a click listener for the submit button
    submitButton.setOnClickListener {
        if (selectedOptions.isNotEmpty()) {
            Toast.makeText(applicationContext, "Selected Options: ${selectedOptions.joinToString(", ")}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "No options selected.", Toast.LENGTH_SHORT).show()
        }
    }


    uploadButton.setOnClickListener {
        // Handle image upload button click
        // You can implement image upload functionality here
        // After uploading, add image to the container
        val imageUri = // The URI of the uploaded image
            addImage(imageUri)
    }
}


}

//This part is to use for searching/radius with be set based on user input
mGeofenceList.add(new Geofence.Builder()
// Set the request ID of the geofence. This is a string to identify this
// geofence.
.setRequestId(entry.getKey())

.setCircularRegion(
entry.getValue().latitude,
entry.getValue().longitude,
Constants.GEOFENCE_RADIUS_IN_METERS
)
.setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
Geofence.GEOFENCE_TRANSITION_EXIT)
.build());




