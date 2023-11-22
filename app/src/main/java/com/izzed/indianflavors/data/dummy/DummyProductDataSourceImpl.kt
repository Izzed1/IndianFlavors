package com.izzed.indianflavors.data.dummy

import com.izzed.indianflavors.model.Menu

interface DummyProductDataSource {
    fun getProducts(): List<Menu>
}

class DummyProductDataSourceImpl() : DummyProductDataSource {
    override fun getProducts(): List<Menu> = listOf(
        Menu(
            name = "Keema Biryani",
            price = 42000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_keema_biryani2.png?raw=true",
            desc = "Keema Biryani is a flavorful Indian dish consisting of spiced ground meat (usually lamb or chicken) cooked with fragrant rice and aromatic spices. The meat is sautéed with onions, tomatoes, and a blend of spices before being layered with parboiled rice and slow-cooked until the flavors meld together. This dish offers a delicious blend of tender meat and perfectly cooked rice, making it a popular choice in Indian cuisine."
        ),
        Menu(
            name = "Chicken Curry",
            price = 50000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_chicken_curry.png?raw=true",
            desc = "Chicken curry is a classic and versatile dish in Indian cuisine. It consists of tender pieces of chicken cooked in a rich and flavorful sauce made from a combination of spices, tomatoes, onions, and sometimes yogurt or cream. The dish can vary in spiciness and flavor profiles depending on regional preferences, and it's often served with rice or bread like naan or roti."
        ),
        Menu(
            name = "Butter Chicken",
            price = 50000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_butter_chicken.png?raw=true",
            desc = "Butter chicken, also known as \"Murgh Makhani\" in Indian cuisine, is a popular and indulgent dish. It features tender pieces of chicken that are marinated in a mixture of yogurt and spices, then grilled or cooked in a tandoor (clay oven) to achieve a smoky flavor and charred texture. The cooked chicken is then simmered in a rich and creamy tomato-based sauce, which is typically flavored with butter, cream, and a blend of aromatic spices. Butter chicken is known for its mild and creamy taste and is often served with naan bread or rice."
        ),
        Menu(
            name = "Naan",
            price = 10000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_naan.png?raw=true",
            desc = "Naan is a traditional flatbread originating from South Asia, particularly India, and it's a staple in Indian, Pakistani, and other South Asian cuisines. It is typically made from a simple dough of wheat flour, water, yeast or yogurt, and a pinch of salt, which is rolled out into a flat, oval or round shape. The dough is then baked in a tandoor, a clay oven, which gives naan its characteristic chewy texture and slightly charred appearance."
        ),
        Menu(
            name = "Chicken Tandoori",
            price = 50000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_chicken_tandoori.png?raw=true",
            desc = "Chicken tandoori is a popular Indian dish known for its vibrant flavors and distinctive cooking method. It involves marinating chicken pieces, often drumsticks or boneless chunks, in a mixture of yogurt and a blend of spices, including cumin, coriander, turmeric, paprika, and cayenne pepper. This marinated chicken is left to absorb the flavors for several hours, resulting in a tender and flavorful meat."
        ),
        Menu(
            name = "Panipuri",
            price = 22000,
            imgUrl = "https://github.com/Izzed1/asset-code_challenge/blob/main/Assets/img_item_panipuri.png?raw=true",
            desc = "Pani Puri, also known as Golgappa or Puchka in different regions of India, is a popular street food and snack. It consists of small, round, hollow, and crispy puris (fried dough balls) that are filled with a mixture of spicy and tangy flavored water, tamarind chutney, mashed potatoes, chickpeas, and a blend of spices."
        )
    )
}
