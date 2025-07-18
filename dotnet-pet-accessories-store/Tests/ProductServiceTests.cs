// Tests/ProductServiceTests.cs
using Microsoft.EntityFrameworkCore;
using PetAccessoriesStore.Data;
using PetAccessoriesStore.Models;
using PetAccessoriesStore.Services;
using Xunit;

namespace PetAccessoriesStore.Tests
{
    public class ProductServiceTests : IDisposable
    {
        private readonly ApplicationDbContext _context;
        private readonly ProductService _productService;

        public ProductServiceTests()
        {
            var options = new DbContextOptionsBuilder<ApplicationDbContext>()
                .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
                .Options;

            _context = new ApplicationDbContext(options);
            _productService = new ProductService(_context);

            // Seed test data
            SeedTestData();
        }

        private void SeedTestData()
        {
            var products = new List<Product>
            {
                new Product
                {
                    Id = 1,
                    Name = "Test Dog Toy",
                    Description = "A fun toy for dogs",
                    Price = 15.99m,
                    Stock = 10,
                    Category = "TOYS",
                    ImageUrl = "https://example.com/toy.jpg",
                    IsActive = true
                },
                new Product
                {
                    Id = 2,
                    Name = "Premium Cat Food",
                    Description = "High-quality nutrition for cats",
                    Price = 35.99m,
                    Stock = 25,
                    Category = "FOOD",
                    ImageUrl = "https://example.com/food.jpg",
                    IsActive = true
                },
                new Product
                {
                    Id = 3,
                    Name = "Inactive Product",
                    Description = "This product is inactive",
                    Price = 10.00m,
                    Stock = 0,
                    Category = "TOYS",
                    ImageUrl = "https://example.com/inactive.jpg",
                    IsActive = false
                }
            };

            _context.Products.AddRange(products);
            _context.SaveChanges();
        }

        [Fact]
        public async Task GetAllProductsAsync_ShouldReturnOnlyActiveProducts()
        {
            // Act
            var result = await _productService.GetAllProductsAsync();

            // Assert
            Assert.Equal(2, result.Count());
            Assert.All(result, p => Assert.True(p.IsActive));
        }

        [Fact]
        public async Task GetProductByIdAsync_WithValidId_ShouldReturnProduct()
        {
            // Act
            var result = await _productService.GetProductByIdAsync(1);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Test Dog Toy", result.Name);
            Assert.Equal(15.99m, result.Price);
        }

        [Fact]
        public async Task GetProductByIdAsync_WithInvalidId_ShouldReturnNull()
        {
            // Act
            var result = await _productService.GetProductByIdAsync(999);

            // Assert
            Assert.Null(result);
        }

        [Fact]
        public async Task GetProductDetailsAsync_WithValidId_ShouldReturnProductDetails()
        {
            // Act
            var result = await _productService.GetProductDetailsAsync(1);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Test Dog Toy", result.Product.Name);
            Assert.NotEmpty(result.Features);
            Assert.Contains("Material", result.Features.Keys);
        }

        [Fact]
        public async Task GetProductDetailsAsync_WithInvalidId_ShouldReturnNull()
        {
            // Act
            var result = await _productService.GetProductDetailsAsync(999);

            // Assert
            Assert.Null(result);
        }

        [Fact]
        public async Task GetProductDetailsAsync_ShouldIncludeCategorySpecificFeatures()
        {
            // Act
            var toyResult = await _productService.GetProductDetailsAsync(1);
            var foodResult = await _productService.GetProductDetailsAsync(2);

            // Assert
            Assert.NotNull(toyResult);
            Assert.NotNull(foodResult);
            
            // Toy should have toy-specific features
            Assert.Contains("Interactive", toyResult.Features.Keys);
            Assert.Contains("Durability", toyResult.Features.Keys);
            
            // Food should have food-specific features
            Assert.Contains("Nutrition", foodResult.Features.Keys);
            Assert.Contains("Ingredients", foodResult.Features.Keys);
        }

        [Fact]
        public async Task GetProductDetailsAsync_ShouldIncludeRelatedProducts()
        {
            // Act
            var result = await _productService.GetProductDetailsAsync(1);

            // Assert
            Assert.NotNull(result);
            Assert.NotNull(result.RelatedProducts);
            // Should not include the same product in related products
            Assert.DoesNotContain(result.RelatedProducts, p => p.Id == 1);
        }

        [Fact]
        public async Task GetProductsByCategoryAsync_ShouldReturnProductsInCategory()
        {
            // Act
            var result = await _productService.GetProductsByCategoryAsync("TOYS");

            // Assert
            Assert.Single(result);
            Assert.Equal("Test Dog Toy", result.First().Name);
        }

        [Fact]
        public async Task GetProductsByCategoryAsync_WithEmptyCategory_ShouldReturnEmpty()
        {
            // Act
            var result = await _productService.GetProductsByCategoryAsync("NONEXISTENT");

            // Assert
            Assert.Empty(result);
        }

        [Fact]
        public async Task SearchProductsAsync_ShouldReturnMatchingProducts()
        {
            // Act
            var result = await _productService.SearchProductsAsync("dog");

            // Assert
            Assert.Single(result);
            Assert.Equal("Test Dog Toy", result.First().Name);
        }

        [Fact]
        public async Task SearchProductsAsync_ShouldSearchInDescription()
        {
            // Act
            var result = await _productService.SearchProductsAsync("nutrition");

            // Assert
            Assert.Single(result);
            Assert.Equal("Premium Cat Food", result.First().Name);
        }

        [Fact]
        public async Task SearchProductsAsync_WithNoMatches_ShouldReturnEmpty()
        {
            // Act
            var result = await _productService.SearchProductsAsync("nonexistent");

            // Assert
            Assert.Empty(result);
        }

        public void Dispose()
        {
            _context.Dispose();
        }
    }
}

