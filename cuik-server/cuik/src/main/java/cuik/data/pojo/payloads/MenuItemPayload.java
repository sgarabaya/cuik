package cuik.data.pojo.payloads;

import java.util.UUID;

import cuik.data.models.Allergen;
import cuik.data.models.NutritionalFacts;

public record MenuItemPayload(
                UUID menuId,
                String name,
                String category,
                String description,
                Float price,
                Boolean available,
                Integer estimatedPrepTime,
                NutritionalFacts nutritionalFacts,
                Allergen[] allergens) {
}
