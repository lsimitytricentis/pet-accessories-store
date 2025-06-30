using PetAccessoriesStore.Models;

namespace PetAccessoriesStore.Data
{
    public static class SeedData
    {
        public static void Initialize(ApplicationDbContext context)
        {
            if (context.Products.Any()) return;

            var products = new List<Product>
            {
                new Product
                {
                    Name = "Squeaky Bone Toy",
                    Description = "Durable rubber bone toy that squeaks",
                    Price = 12.99m,
                    Stock = 50,
                    Category = "TOYS",
                    ImageUrl = "https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400"
                },
                new Product
                {
                    Name = "Premium Dog Food",
                    Description = "High-quality nutrition for your furry friend",
                    Price = 45.99m,
                    Stock = 25,
                    Category = "FOOD",
                    ImageUrl = "https://images.unsplash.com/photo-1589924691995-400dc9ecc119?w=400"
                },
                new Product
                {
                    Name = "Leather Dog Collar",
                    Description = "Stylish and comfortable leather collar",
                    Price = 24.99m,
                    Stock = 30,
                    Category = "COLLARS",
                    ImageUrl = "https://images.unsplash.com/photo-1583337130417-3346a1be7dee?w=400"
                },
                new Product
                {
                    Name = "Cat Scratching Post",
                    Description = "Multi-level scratching post with toys",
                    Price = 89.99m,
                    Stock = 15,
                    Category = "TOYS",
                    ImageUrl = "https://images.unsplash.com/photo-1574158622682-e40e69881006?w=400"
                },
                new Product
                {
                    Name = "Pet Grooming Kit",
                    Description = "Complete grooming set with brushes and nail clippers",
                    Price = 34.99m,
                    Stock = 40,
                    Category = "GROOMING",
                    ImageUrl = "https://images.unsplash.com/photo-1581888227599-779811939961?w=400"
                },
                new Product
                {
                    Name = "Cozy Pet Bed",
                    Description = "Ultra-soft and washable pet bed",
                    Price = 59.99m,
                    Stock = 20,
                    Category = "BEDS",
                    ImageUrl = "https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?w=400"
                },
                new Product
                {
                    Name = "Interactive Puzzle Toy",
                    Description = "Mental stimulation toy for intelligent pets",
                    Price = 19.99m,
                    Stock = 35,
                    Category = "TOYS",
                    ImageUrl = "https://images.unsplash.com/photo-1605568427561-40dd23c2acea?w=400"
                },
                new Product
                {
                    Name = "Retractable Dog Leash",
                    Description = "16ft retractable leash with brake system",
                    Price = 28.99m,
                    Stock = 45,
                    Category = "ACCESSORIES",
                    ImageUrl = "https://images.unsplash.com/photo-1587300003388-59208cc962cb?w=400"
                }
            };

            context.Products.AddRange(products);
            context.SaveChanges();
        }
    }
}
