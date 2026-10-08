const data = {
    "name": "Comida",
    "is_active": true,
    "items": [{
        "name": "Empanadas de Carne Cortada a Cuchillo",
        "description":
            "Empanadas tradicionales horneadas, rellenas de carne cortada a cuchillo, cebolla, aceitunas y huevo duro.",
        "category": "Entradas",
        "price": 4000,
        "available": true,
        "estimated_prep_time": 15,
        "nutritional_facts": {
            "portion": "1 empanada (100g)",
            "calories": 250,
            "protein": "12g",
            "fats": "14g",
            "is_vegan": false,
            "is_gluten_free": false,
        },
        "allergens": ["Gluten", "Huevo"],
    }, {
        "name": "Provoleta",
        "description":
            "Queso provolone asado a la parrilla con orégano y aceite de oliva.",
        "category": "Entradas",
        "price": 8500,
        "available": true,
        "estimated_prep_time": 20,
        "nutritional_facts": {
            "portion": "1 porción (150g)",
            "calories": 500,
            "protein": "35g",
            "fats": "38g",
            "is_vegan": false,
            "is_gluten_free": true,
        },
        "allergens": ["Lácteos"],
    }, {
        "name": "Choripán con Chimichurri",
        "description":
            "Chorizo asado servido en pan francés con el clásico chimichurri.",
        "category": "Entradas",
        "price": 5500,
        "available": true,
        "estimated_prep_time": 20,
        "nutritional_facts": {
            "portion": "1 sándwich (200g)",
            "calories": 520,
            "protein": "22g",
            "fats": "36g",
            "is_vegan": false,
            "is_gluten_free": false,
        },
        "allergens": ["Gluten"],
    }, {
        "name": "Mollejas a la Parrilla",
        "description":
            "Mollejas de vaca crocantes a la parrilla, condimentadas con sal y limón.",
        "category": "Entradas",
        "price": 12000,
        "available": true,
        "estimated_prep_time": 30,
        "nutritional_facts": {
            "portion": "1 porción (200g)",
            "calories": 480,
            "protein": "28g",
            "fats": "40g",
            "is_vegan": false,
            "is_gluten_free": true,
        },
        "allergens": [],
    }, {
        "name": "Asado de Tira",
        "description":
            "El clásico asado de tira argentino hecho a la leña, servido con chimichurri.",
        "category": "Principales",
        "price": 22000,
        "available": true,
        "estimated_prep_time": 45,
        "nutritional_facts": {
            "portion": "1 porción (400g)",
            "calories": 1100,
            "protein": "65g",
            "fats": "90g",
            "is_vegan": false,
            "is_gluten_free": true,
        },
        "allergens": [],
    }, {
        "name": "Milanesa a la Napolitana",
        "description":
            "Milanesa de carne de vaca cubierta con jamón cocido, salsa de tomate y queso muzzarella derretido.",
        "category": "Principales",
        "price": 16000,
        "available": true,
        "estimated_prep_time": 35,
        "nutritional_facts": {
            "portion": "1 porción (350g)",
            "calories": 850,
            "protein": "45g",
            "fats": "40g",
            "is_vegan": false,
            "is_gluten_free": false,
        },
        "allergens": ["Gluten", "Lácteos", "Huevo"],
    }, {
        "name": "Bife de Chorizo",
        "description":
            "Bife de chorizo de corte grueso, hecho a la parrilla al punto deseado.",
        "category": "Principales",
        "price": 25000,
        "available": true,
        "estimated_prep_time": 40,
        "nutritional_facts": {
            "portion": "1 bife (350g)",
            "calories": 800,
            "protein": "85g",
            "fats": "50g",
            "is_vegan": false,
            "is_gluten_free": true,
        },
        "allergens": [],
    }, {
        "name": "Sorrentinos de Jamón y Queso",
        "description":
            "Sorrentinos grandes rellenos de jamón y muzzarella, servidos con salsa tuco.",
        "category": "Principales",
        "price": 14000,
        "available": true,
        "estimated_prep_time": 30,
        "nutritional_facts": {
            "portion": "1 porción (300g)",
            "calories": 650,
            "protein": "30g",
            "fats": "28g",
            "is_vegan": false,
            "is_gluten_free": false,
        },
        "allergens": ["Gluten", "Lácteos", "Huevo"],
    }],
};

function createTree(key, value) {
    if (value !== null && typeof value === "object") {
        const details = document.createElement("details");
        const summary = document.createElement("summary");
        const isArray = Array.isArray(value);
        const typeIndicator = isArray
            ? `Array[${value.length}]
`
            : "Object";
        summary.textContent = `${key} { ${typeIndicator} }`;
        details.appendChild(summary);
        for (const k in value) {
            details.appendChild(createTree(k, value[k]));
        }
        return details;
    } else {
        const div = document.createElement("div");
        div.className = "primitive";
        let valueClass = typeof value;
        let displayValue = value;
        if (typeof value === "string") {
            displayValue = `"${value}"`;
        }
        div.innerHTML = `<span class="key">
${key}:</span> <span class="${valueClass}">
${displayValue}
</span>`;
        return div;
    }
}
document.getElementById("tree-container").appendChild(createTree("root", data));
