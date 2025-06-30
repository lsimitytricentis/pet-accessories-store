namespace PetAccessoriesStore.Models.DTOs
{
    public class ProductDetailsResponse
    {
        public Product Product { get; set; } = null!;
        public Dictionary<string, string> Features { get; set; } = new Dictionary<string, string>();
        public List<Product> RelatedProducts { get; set; } = new List<Product>();
    }
}