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
package codeu.orm;

import codeu.model.data.Dish;
import codeu.model.store.basic.ReviewStore;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Wrapper class that loads information from Data Store,
 * handles the setting & getting of information for Dishes,
 * and abstracts the process of querying.
 */
public class DishORM {
    private Map<UUID, Dish> dishMap; // maps dishIDs to Dish objects
    private Map<UUID, Integer> avgRatingMap; // maps dishIDs to average star ratings

    public DishORM(Map<UUID, Dish> dishMap, Map<UUID, Integer> avgRatingMap) {
        this.dishMap = dishMap;
        this.avgRatingMap = avgRatingMap;
    }

    /**
     * Retrieves the Dish object associated with the given id.
     */
    public Dish getDish(UUID id) {
        return this.dishMap.get(id);
    }

    /**
     * Should only be called in the context that the Dish exists,
     * since we're querying with some known dishID.
     * @param id of the dish we want avg rating of
     * @return INTEGER average rating associated with the given dish.
     */
    public int getAverageRating(UUID id) {
        return this.avgRatingMap.get(id);
    }

    /**
     * Used to calculate the average rating for each dish.
     * Also can be used as a display/UI detail (i.e. # of reviews per dish).
     * @param id of the dish
     * @return INTEGER number of reviews per dish.
     */
    public int getNumReviews(UUID id) {
        return ReviewStore.getInstance().getNumReviews(id);
    }

    /**
     * Good for UI display (i.e. showing _all_ tags for one dish for the user)
     * Returns the tags for the dish mapped by CATEGORY.
     *
     * @param id id of the dish we want all tags of
     * @return all the tags for a particular Dish || {tag type : {tag values}}
     */
    public Map<String, Set<String>> getTagsForDish(UUID id) {
        Dish dish = getDish(id);
        return dish.getTags();
    }

    public Set<String> getAllTagsForDish(UUID dishID) {
        return getDish(dishID).getAllTagValues();
    }

    /**
     * Used for Testing purposes.
     */
    public Map<UUID, Dish> getDishMap() {
        return this.dishMap;
    }

    /**
     * Used for Testing purposes.
     */
    public Map<UUID, Integer> getAvgRatingMap() {
        return this.avgRatingMap;
    }

    /**
     * Use when adding in a NEW DISH for the first time into our DishORM memory.
     * Puts it in the dishMap {dishID : Dish object}
     * Also adds the only rating into avgRatingMap {dishID : "average" rating}
     * @param dish object
     */
    public void addDish(Dish dish) {
        this.dishMap.put(dish.getDishID(), dish);
        this.avgRatingMap.put(dish.getDishID(), dish.getRating());
    }

    /**
     * Use when a user enters a NEW review for this Dish, so we need to update Dish's avg rating.
     * Also use during LOADING from PDS to update avgRatingMap.
     * Should always be called when the Dish already exists in our HashMap.
     * Should also be called BEFORE the new review is added into ReviewStore.
     * TODO: should we handle the mistake of calling this function with an irrelevant dishID?
     *
     * @param id of the dish
     * @param rate incoming rating we factor into the avg
     * @return Dish object with the updated avg rating.
     */
    public Dish updateRating(UUID id, int rate) {
        Dish updatedDish = getDish(id);
        int oldRating = getAverageRating(id);

        int prevNumReviews = getNumReviews(id);
        int newRating = (oldRating * prevNumReviews + rate) / (prevNumReviews + 1);
        updatedDish.setRating(newRating);
        avgRatingMap.put(id, newRating);
        return updatedDish;
    }

    /**
     * After user tags a particular Dish, DishORM will update that Dish's
     * tag values within the Dish object via this Handler class.
     *
     * @param id       id of the Dish that was just rated/tagged
     * @param userTags all the userTags of the form {tagType : {tagValues}}
     * @return the Dish object that was updated to be rewritten into DataStore
     * @method updateDishTags
     */
    public Dish updateDishTags(UUID id, Map<String, Set<String>> userTags) {
        Dish dish = getDish(id);
        dish.addUserTags(userTags);
        return dish;
    }

    /**
     * Used in Testing files to have custom equality checks.
     */
    @Override
    public boolean equals(Object o) {
        DishORM orm = (DishORM) o;
        return orm.getDishMap().equals(this.dishMap)
                && orm.getAvgRatingMap().equals(this.avgRatingMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishMap, avgRatingMap);
    }
}
