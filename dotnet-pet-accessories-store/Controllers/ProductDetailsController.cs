// Controllers/ProductDetailsController.cs (API Controller)
using Microsoft.AspNetCore.Mvc;
using PetAccessoriesStore.Services;

namespace PetAccessoriesStore.Controllers
{
    [ApiController]
    [Route("api/product-details")]
    public class ProductDetailsController : ControllerBase
    {
        private readonly IProductService _productService;

        public ProductDetailsController(IProductService productService)
        {
            _productService = productService;
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetProductDetails(int id)
        {
            var details = await _productService.GetProductDetailsAsync(id);
            if (details == null)
            {
                return NotFound();
            }
            return Ok(details);
        }
    }
}
