import { ActivatedRoute, Router } from '@angular/router';
import { Product } from './../product.model';
import { ProductService } from './../product.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-product-update',
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css']
})
export class ProductUpdateComponent implements OnInit {

  constructor(private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute) { }

  product: Product;

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.productService.readById(id).subscribe(product => {
      this.product = product;
    });
  }

  updateProduct(): void {
    this.productService.update(this.product).subscribe(() => {
      this.productService.showMessage('Product updated successfully!');
      this.router.navigate(['/products']);
    })

  }

  cancel(): void {
    this.router.navigate(['/products']);
  }

}
