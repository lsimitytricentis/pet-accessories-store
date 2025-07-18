// Tests/ProductsControllerTests.cs
using Microsoft.AspNetCore.Mvc;
using Moq;
using PetAccessoriesStore.Controllers;
using PetAccessoriesStore.Models;
using PetAccessoriesStore.Services;
using Xunit;

namespace PetAccessoriesStore.Tests
{
    public class ProductsControllerTests
    {
        private readonly Mock<IProductService> _mockProductService;
        private readonly ProductsController _controller;

        public ProductsControllerTests()
        {
            _mockProductService = new Mock<IProductService>();
            _controller = new ProductsController(_mockProductService.Object);
        }

        [Fact]
        public async Task GetProducts_ShouldReturnOkWithProducts()
        {
            // Arrange
            var products = new List<Product>
            {
                new Product { Id = 1, Name = "Product 1", Price = 10.99m },
                new Product { Id = 2, Name = "Product 2", Price = 20.99m }
            };

            _mockProductService.Setup(s => s.GetAllProductsAsync())
                .ReturnsAsync(products);

            // Act
            var result = await _controller.GetProducts();

            // Assert
            var okResult = Assert.IsType<OkObjectResult>(result);
            var returnedProducts = Assert.IsAssignableFrom<IEnumerable<Product>>(okResult.Value);
            Assert.Equal(2, returnedProducts.Count());
        }

        [Fact]
        public async Task GetProduct_WithValidId_ShouldReturnOkResult()
        {
            // Arrange
            var product = new Product
            {
                Id = 1,
                Name = "Test Product",
                Price = 15.99m,
                Stock = 10
            };

            _mockProductService.Setup(s => s.GetProductByIdAsync(1))
                .ReturnsAsync(product);

            // Act
            var result = await _controller.GetProduct(1);

            // Assert
            var okResult = Assert.IsType<OkObjectResult>(result);
            var returnedProduct = Assert.IsType<Product>(okResult.Value);
            Assert.Equal("Test Product", returnedProduct.Name);
        }

        [Fact]
        public async Task GetProduct_WithInvalidId_ShouldReturnNotFound()
        {
            // Arrange
            _mockProductService.Setup(s => s.GetProductByIdAsync(999))
                .ReturnsAsync((Product?)null);

            // Act
            var result = await _controller.GetProduct(999);

            // Assert
            Assert.IsType<NotFoundResult>(result);
        }

        [Fact]
        public async Task GetProductsByCategory_ShouldReturnFilteredProducts()
        {
            // Arrange
            var categoryProducts = new List<Product>
            {
                new Product { Id = 1, Name = "Dog Toy", Category = "TOYS" }
            };

            _mockProductService.Setup(s => s.GetProductsByCategoryAsync("TOYS"))
                .ReturnsAsync(categoryProducts);

            // Act
            var result = await _controller.GetProductsByCategory("TOYS");

            // Assert
            var okResult = Assert.IsType<OkObjectResult>(result);
            var returnedProducts = Assert.IsAssignableFrom<IEnumerable<Product>>(okResult.Value);
            Assert.Single(returnedProducts);
            Assert.Equal("TOYS", returnedProducts.First().Category);
        }
    }
}

