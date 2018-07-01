// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;

/** Class representing an individual Review associated with a particualar dish */
public class Review {
    private final UUID reviewID;
    private final UUID author;
    private final UUID dishID;
    private final int numStars;
    private final String desc;
    private final HashMap<String, Set<String>> tags;
//  private final Photo photo; // LATER FEATURE

    /**
     * Constructs a new Review.
     *
     * @param id           the ID of the review
     * @param author       the author of the Review
     * @param dishID       the ID of the dish
     * @param numStars     the number of stars this _specific_ review gave to Dish
     * @param desc         the text body of the actual review
     */
    public Review(UUID id, UUID author, UUID dishID, int numStars, String desc, HashMap<String, Set<String>> tags) {
        this.reviewID = id;
        this.author = author;
        this.dishID = dishID;
        this.numStars = numStars;
        this.desc = desc;
        this.tags = tags;
    }

    /**
     * Returns the UUID of the Review
     */
    public UUID getReviewID() {
        return this.reviewID;
    }

    /**
     * Returns the author UUID of the Review
     */
    public UUID getAuthor() {
        return this.author;
    }

    /**
     * Returns the id of the dish
     */
    public UUID getDishID() {
        return this.dishID;
    }

    /**
     * Returns the star rating of this particualr Review
     */
    public int getStarRating() {
        return this.numStars;
    }

    /**
     * Returns the text description of the Review
     */
    public String getDescription() {
        return this.desc;
    }

    /**
     * Returns the text description of the Review
     */
    public HashMap<String, Set<String>> getTags() {
        return this.tags;
    }
}

