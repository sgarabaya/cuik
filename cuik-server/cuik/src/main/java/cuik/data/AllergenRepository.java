package cuik.data;

import cuik.data.models.Allergen;

public class AllergenRepository extends Repository<Allergen> {
    public AllergenRepository(SqlClient client) {
        super(client);
    }

    @Override
    protected Class<Allergen> getModelClass() {
        return Allergen.class;
    }

    @Override
    protected String getTableName() {
        return "Allergens";
    }
}
