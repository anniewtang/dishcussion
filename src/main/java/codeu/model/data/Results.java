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

import java.util.*;

import codeu.model.store.basic.ReviewStore;
import codeu.model.data.Review;
import codeu.model.data.Dish;

/** Class representing search results. */
public class Results {

  private ArrayList<Dish> myResults;
  private String entry;

  /**
   * Constructs a new Feed.
   * @param userEntry what the user put in search bar
   */
  public Results(String userEntry) {
    entry = userEntry;
    myResults = new ArrayList<Dish>();
    fillResults();
  }

  /** Find and include all matching results. */
  public void fillResults() {
    // TODO: adding some default dishes to test display
    ReviewStore reviewStore =  ReviewStore.getInstance();

    Dish dish1 = new Dish(UUID.randomUUID(), "California Roll", "Katana Sushi", 4, null, null);
    Dish dish2 = new Dish(UUID.randomUUID(), "Beef Barg", "Shamshiri Grill", 3, null, null);

    Map<String, Set<String>> tags = new HashMap<>();
    tags.put(Constants.RESTRICTION, new HashSet<>(Arrays.asList(Constants.DAIRYFREE)));
    tags.put(Constants.CUISINE, new HashSet<>(Arrays.asList(Constants.JAPANESE)));

    myResults.add(dish1); // TODO: ask about making ratings doubles not ints
    myResults.add(dish2);

    Review review1 = new Review(UUID.randomUUID(), UUID.randomUUID(), dish1.getDishID(), 4, "OMG the cilantro roll is AMAZING!!", tags);
    Review review2 = new Review(UUID.randomUUID(), UUID.randomUUID(), dish2.getDishID(), 4, "Juciest meat ever! Get as medium rare!", tags);

    reviewStore.addReview(review1);
    reviewStore.addReview(review2);
  }

  /** Add dish to results. */
  public void addDishToResults(/** Not sure how to structure this yet **/) {
    // TODO: might be useful to capture order nuances - prob eliminating this function
  }

  /** Return result by index. */
  public Dish getResult(int index) {
    return myResults.get(index);
  }

  /** Access event type. */
  public int getResultsCount() {
    return myResults.size();
  }

  /** Sort results by ratings **/
  public void sortByRatings() {
    // TODO: implement
  }

}
