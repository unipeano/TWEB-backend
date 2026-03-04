DROP TABLE IF EXISTS Users,
Recipes,
Ingredients,
RecipeIngredients;


CREATE TABLE Users(
id SERIAL PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(20) NOT NULL,
role VARCHAR(20) CHECK (role IN ('amateur', 'chef', 'admin')) DEFAULT 'amateur',
--profile_pic     TEXT -- URL immagine profilo
bio TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Recipes(
id              SERIAL PRIMARY KEY,
title           VARCHAR(100) NOT NULL,
description     TEXT,
instructions    TEXT NOT NULL,
--image_url       TEXT,
user_id         INTEGER REFERENCES Users(id) ON DELETE CASCADE,
is_public       BOOLEAN DEFAULT TRUE,
created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Ingredients(
id              SERIAL PRIMARY KEY,
name            VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE RecipeIngredients(
recipe_id       INTEGER REFERENCES Recipes(id) ON DELETE CASCADE,
ingredient_id   INTEGER REFERENCES Ingredients(id) ON DELETE CASCADE,
quantity        VARCHAR(100),
PRIMARY KEY (recipe_id, ingredient_id)
);
