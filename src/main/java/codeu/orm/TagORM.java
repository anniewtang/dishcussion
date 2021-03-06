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

import codeu.model.data.Tag;
import codeu.model.store.basic.DishStore;

import java.util.Map.Entry;

import java.util.*;

/**
 * Wrapper class that loads information from Data Store,
 * handles the setting & getting of information for Tags,
 * and abstracts away the process of querying for SEARCH.
 *
 * Can return the DISHES associated with TAG VALUES.
 */
public class TagORM {

    // maps tag categories to their associated Tag object
    private Map<String, Tag> tagsByType;

    public TagORM(Map<String, Tag> tagsByType) {
        this.tagsByType = tagsByType;
    }

    /**
     * Retrieves the Tag object associated with a particular category.
     * i.e. "cuisine", "restriction", etc.
     * Good for Search querying.
     *
     * @param type name of the tag category
     * @return appropriate Tag object
     * @method getTagForType
     */
    public Tag getTagForType(String type) {
        return tagsByType.computeIfAbsent(type, Tag::new);
    }

    /**
     * Primarily used for testing purposes.
     * Can also be used when querying, if useful.
     * @return private map of {Tag Category/Type : Tag object}
     */
    public Map<String, Tag> getTagsByTypeMap() {
        return this.tagsByType;
    }

    public Set<UUID> getDishesByValue(Tag tag, String value) {
        return tag.getDishesByValue(value);
    }


    /**
     * After User provides user-tags for a particular Dish,
     * we add Dish into all the appropriate Tag categories it belongs to.
     *
     * @param id       dish ID user entered tags for
     * @param userTags {tagType : {tagValues}} || i.e. {restrictions: {vegan, vegetarian}}
     * @return a Set of all the Tag objects that we updated so we can write them into Data Store
     * @method setTags
     */
    public Set<Tag> updateTags(UUID id, Map<String, Set<String>> userTags) {
        Set<Tag> updatedTags = new HashSet<>();
        for (Entry<String, Set<String>> tagEntry : userTags.entrySet()) {
            String tagType = tagEntry.getKey();
            Set<String> tagValues = userTags.get(tagType);

            Tag tag = getTagForType(tagType);
            tag.addDishToTagValues(tagValues, id);

            updatedTags.add(tag);
        }
        return updatedTags;
    }

    /**
     * Used in Testing files to have custom equality checks.
     */
    @Override
    public boolean equals(Object o) {
        TagORM orm = (TagORM) o;
        return orm.getTagsByTypeMap().equals(this.tagsByType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagsByType);
    }
}
