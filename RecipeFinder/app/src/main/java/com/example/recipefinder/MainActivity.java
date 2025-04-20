package com.example.recipefinder;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int VOICE_INPUT_REQUEST_CODE = 100;
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> allRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        Button voiceButton = findViewById(R.id.voiceButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        allRecipes = getRecipeData();  // Load all recipes initially

        voiceButton.setOnClickListener(v -> startVoiceInput());
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            startActivityForResult(intent, VOICE_INPUT_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Your device doesn't support speech input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_INPUT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0).toLowerCase();

            List<Recipe> filteredRecipes = new ArrayList<>();
            for (Recipe recipe : allRecipes) {
                if (recipe.getName().toLowerCase().contains(spokenText)) {
                    filteredRecipes.add(recipe);
                }
            }

            if (filteredRecipes.isEmpty()) {
                Toast.makeText(this, "No matching recipe found.", Toast.LENGTH_SHORT).show();
            }

            adapter.setRecipes(filteredRecipes);
        }
    }

    private List<Recipe> getRecipeData() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe("Pasta", "Pasta, Tomato Sauce, Cheese", "Boil pasta, add sauce, and cheese."));
        recipes.add(new Recipe("Fried Rice", "Rice, Vegetables, Soy Sauce", "Fry veggies, add rice and soy sauce."));
        recipes.add(new Recipe("Pancake", "Flour, Eggs, Milk", "Mix and cook on a skillet."));

        return recipes;
    }
}
