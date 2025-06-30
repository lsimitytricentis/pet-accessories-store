using Microsoft.EntityFrameworkCore;
using PetAccessoriesStore.Data;
using PetAccessoriesStore.Models;
using PetAccessoriesStore.Models.DTOs;

namespace PetAccessoriesStore.Services
{
    public class ProductService : IProductService
    {
        private readonly ApplicationDbContext _context;

        public ProductService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<IEnumerable<Product>> GetAllProductsAsync()
        {
            return await _context.Products
                .Where(p => p.IsActive)
                .OrderBy(p => p.Name)
                .ToListAsync();
        }

        public async Task<IEnumerable<Product>> GetProductsByCategoryAsync(string category)
        {
            return await _context.Products
                .Where(p => p.Category == category && p.IsActive)
                .OrderBy(p => p.Name)
                .ToListAsync();
        }

        public async Task<Product?> GetProductByIdAsync(int id)
        {
            return await _context.Products.FindAsync(id);
        }

        public async Task<ProductDetailsResponse?> GetProductDetailsAsync(int id)
        {
            var product = await _context.Products.FindAsync(id);
            if (product == null) return null;

            var features = GetProductFeatures(product);
            var relatedProducts = await GetRelatedProductsAsync(product);

            return new ProductDetailsResponse
            {
                Product = product,
                Features = features,
                RelatedProducts = relatedProducts
            };
        }

        private Dictionary<string, string> GetProductFeatures(Product product)
        {
            var features = new Dictionary<string, string>();

            switch (product.Category)
            {
                case "TOYS":
                    features.Add("Material", "Safe, non-toxic materials");
                    features.Add("Age Range", "All ages");
                    features.Add("Interactive", "Promotes active play");
                    features.Add("Durability", "Built to last");
                    break;
                case "FOOD":
                    features.Add("Nutrition", "Complete and balanced");
                    features.Add("Ingredients", "Premium quality");
                    features.Add("No Artificial", "No artificial preservatives");
                    features.Add("Vet Recommended", "Veterinarian approved");
                    break;
                case "ACCESSORIES":
                    features.Add("Material", "High-quality construction");
                    features.Add("Design", "Practical and stylish");
                    features.Add("Easy Care", "Simple maintenance");
                    features.Add("Versatile", "Multi-purpose use");
                    break;
                case "GROOMING":
                    features.Add("Professional Grade", "Salon-quality tools");
                    features.Add("Ergonomic", "Comfortable grip");
                    features.Add("Safe", "Rounded edges for safety");
                    features.Add("Complete Set", "All-in-one solution");
                    break;
                case "BEDS":
                    features.Add("Comfort", "Ultra-soft materials");
                    features.Add("Support", "Orthopedic design");
                    features.Add("Washable", "Machine washable cover");
                    features.Add("Size Options", "Multiple sizes available");
                    break;
                case "COLLARS":
                    features.Add("Adjustable", "Perfect fit guarantee");
                    features.Add("Durable", "Weather-resistant");
                    features.Add("Comfortable", "Padded for comfort");
                    features.Add("Secure", "Heavy-duty hardware");
                    break;
                default:
                    features.Add("Quality", "Premium materials");
                    features.Add("Design", "Thoughtful construction");
                    features.Add("Value", "Great value for money");
                    features.Add("Satisfaction", "100% satisfaction guaranteed");
                    break;
            }

            return features;
        }

        private async Task<List<Product>> GetRelatedProductsAsync(Product product)
        {
            return await _context.Products
                .Where(p => p.Category == product.Category && p.Id != product.Id && p.IsActive)
                .Take(4)
                .ToListAsync();
        }

        public async Task<Product> AddProductAsync(Product product)
        {
            _context.Products.Add(product);
            await _context.SaveChangesAsync();
            return product;
        }

        public async Task<Product> UpdateProductAsync(Product product)
        {
            _context.Products.Update(product);
            await _context.SaveChangesAsync();
            return product;
        }

        public async Task<bool> DeleteProductAsync(int id)
        {
            var product = await _context.Products.FindAsync(id);
            if (product == null) return false;

            product.IsActive = false;
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<IEnumerable<Product>> SearchProductsAsync(string searchTerm)
        {
            return await _context.Products
                .Where(p => p.IsActive && 
                           (p.Name.Contains(searchTerm) || p.Description.Contains(searchTerm)))
                .OrderBy(p => p.Name)
                .ToListAsync();
        }
    }
}
