package com.mudra.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mudra.R;
import com.mudra.Urls_Api.Url_Links;
import com.mudra.custom_fonts.TypefaceEditText;
import com.mudra.custom_fonts.TypefaceTextView;
import com.mudra.model.ProductListObject;
import com.mudra.model.ProductListResponse;
import com.mudra.utils.FragmentActivityMessage;
import com.mudra.utils.GlobalBus;
import com.mudra.utils.Util;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-02-2018.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private ArrayList<ProductListObject> categoryItem;
    private Context mContext;
    private String from;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, remove, add;
        TypefaceTextView name, cname, price, status;
        TypefaceEditText quantity;
        RelativeLayout checkedLayout, container;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            remove = (ImageView) view.findViewById(R.id.remove);
            add = (ImageView) view.findViewById(R.id.add);

            quantity = (TypefaceEditText) view.findViewById(R.id.quantity);
            name = (TypefaceTextView) view.findViewById(R.id.name);
            cname = (TypefaceTextView) view.findViewById(R.id.cname);
            price = (TypefaceTextView) view.findViewById(R.id.price);
            status = (TypefaceTextView) view.findViewById(R.id.status);

            checkedLayout = (RelativeLayout) view.findViewById(R.id.checkedLayout);
            container = (RelativeLayout) view.findViewById(R.id.container);
        }
    }

    public ProductListAdapter(ArrayList<ProductListObject> categoryItems, Context mContext, String from) {
        this.categoryItem = categoryItems;
        this.mContext = mContext;
        this.from = from;
    }

    @Override
    public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_adapter, parent, false);

        return new ProductListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.MyViewHolder holder, final int position) {
        final ProductListObject categoryItemTemp = categoryItem.get(position);

        if (from.equalsIgnoreCase("cart")) {
            holder.quantity.setVisibility(View.VISIBLE);
            holder.remove.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.VISIBLE);
        } else {
            holder.quantity.setVisibility(View.GONE);
            holder.remove.setVisibility(View.GONE);
            holder.add.setVisibility(View.GONE);
        }

        if (categoryItemTemp.getName() != null)
            holder.name.setText(categoryItemTemp.getName());
        if (categoryItemTemp.getCname() != null)
            holder.cname.setText("By : " + categoryItemTemp.getCname());
        if (categoryItemTemp.getPrice() != null)
            holder.price.setText("MRP : " + mContext.getResources().getString(R.string.Rs) + " " + categoryItemTemp.getPrice());
        if (categoryItemTemp.getStatus() != null)
            holder.status.setText("Status : " + categoryItemTemp.getStatus());

        holder.quantity.setText("" + categoryItemTemp.getCount());

        if (from.equalsIgnoreCase("cart")) {
            holder.quantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    itemAddRemoveQuant(categoryItemTemp, 1, position, true, holder.quantity.getText().toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        Glide.with(mContext).load(Url_Links.Image_Url + categoryItemTemp.getImage())
                .error(R.drawable.imagenotavailable)
                .into(holder.image);

        if (!from.equalsIgnoreCase("cart")) {
            if (Util.getProductToCart(mContext) != null) {
                if (Util.getProductToCart(mContext).contains(categoryItemTemp.getName())) {
                    holder.checkedLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.checkedLayout.setVisibility(View.GONE);
                }
            }

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemAddRemove(categoryItemTemp, 0, position, false, "");

                    FragmentActivityMessage activityActivityMessage =
                            new FragmentActivityMessage("", "cartupdate");
                    GlobalBus.getBus().post(activityActivityMessage);

                }
            });
        }

        holder.add.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                itemAddRemove(categoryItemTemp, 1, position, false, "");
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                itemAddRemove(categoryItemTemp, 2, position, false, "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryItem.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void itemAddRemoveQuant(ProductListObject categoryItemTemp, int i, int position, boolean flagFrom, String quant) {

        if (quant != null && quant.length() > 0 && Integer.parseInt(quant) > 0) {


            String cartItem = Util.getCartItem(mContext);
            ProductListResponse productListResponse = new Gson().fromJson(cartItem, ProductListResponse.class);
            ArrayList<ProductListObject> productListObjects = new ArrayList<>();
            productListObjects = productListResponse.getList();
            categoryItemTemp.setCount(Integer.parseInt(quant));

            ProductListResponse listResponse = new ProductListResponse();
            for (ProductListObject productListObject : productListObjects) {

                if (productListObject.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                    productListObject.setCount(Integer.parseInt(quant));

                    listResponse.setList(categoryItem);
                    Util.setCartItem(mContext, new Gson().toJson(listResponse));
                }
            }
        }
    }

    public void itemAddRemove(ProductListObject categoryItemTemp, int i, int position, boolean flagFrom, String quant) {
        String cartItem = Util.getCartItem(mContext);
        if (cartItem != null && cartItem.length() > 0) {
            ProductListResponse productListResponse = new Gson().fromJson(cartItem, ProductListResponse.class);
            ArrayList<ProductListObject> productListObjects = new ArrayList<>();
            productListObjects = productListResponse.getList();

            if (i == 0) {
                ProductListResponse productListResponse1 = new ProductListResponse();
                boolean flag = false;

                for (ProductListObject productListObject : productListObjects) {
                    if (productListObject.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    categoryItemTemp.setCount(1);
                    productListObjects.add(categoryItemTemp);
                    productListResponse1.setList(productListObjects);
                    Util.setCartItem(mContext, new Gson().toJson(productListResponse1));
                    //Toast.makeText(mContext, "Item added to cart!!", Toast.LENGTH_SHORT).show();
                    if (Util.getProductToCart(mContext) != null)
                        Util.setProductToCart(mContext, Util.getProductToCart(mContext) + "," + categoryItemTemp.getName());
                    else
                        Util.setProductToCart(mContext, categoryItemTemp.getName());

                    notifyDataSetChanged();
                } else {
                    if (Util.getProductToCart(mContext) != null) {
                        if (Util.getProductToCart(mContext).contains(categoryItemTemp.getName())) {
                            Util.setProductToCart(mContext, Util.getProductToCart(mContext).replace(categoryItemTemp.getName(), ""));
                        }
                    }
                    //////////////////////
                    ArrayList<ProductListObject> categoryItem = productListObjects;
                    ProductListResponse listResponse = new ProductListResponse();

                    ProductListResponse productListResponse2 = new Gson().fromJson(cartItem, ProductListResponse.class);
                    ArrayList<ProductListObject> productListObjects1 = new ArrayList<>();
                    productListObjects1 = productListResponse2.getList();


                    for (ProductListObject productListObject : productListObjects1) {

                        if (productListObject.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                            productListObject.setCount(0);
                            int posi = 0;
                            for (ProductListObject productListObject1 : categoryItem) {
                                ++posi;
                                if (productListObject1.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                                    break;
                                }
                            }
                            categoryItem.remove(posi - 1);

                            listResponse.setList(categoryItem);
                            Util.setCartItem(mContext, new Gson().toJson(listResponse));
                            notifyDataSetChanged();
                        }
                    }
                    //////////////////////
                    notifyDataSetChanged();
                    // Toast.makeText(mContext, "Already added to cart!!", Toast.LENGTH_SHORT).show();
                }
                // }

            } else if (i == 1) {
                categoryItemTemp.setCount(categoryItemTemp.getCount() + 1);

                ProductListResponse listResponse = new ProductListResponse();
                for (ProductListObject productListObject : productListObjects) {

                    if (productListObject.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                        if (!flagFrom) {
                            productListObject.setCount(productListObject.getCount() + 1);

                            listResponse.setList(categoryItem);
                            Util.setCartItem(mContext, new Gson().toJson(listResponse));
                            notifyDataSetChanged();
                        } else {
                            //Log.e("heres", "is: " + Integer.parseInt(quant));
                            productListObject.setCount(Integer.parseInt(quant));

                            listResponse.setList(categoryItem);
                            Util.setCartItem(mContext, new Gson().toJson(listResponse));
                        }
                    }
                }

            } else {
                if (categoryItemTemp.getCount() == 1) {

                    categoryItemTemp.setCount(categoryItemTemp.getCount() - 1);
                    categoryItem.remove(position);

                    ProductListResponse listResponse = new ProductListResponse();
                    listResponse.setList(categoryItem);
                    Util.setCartItem(mContext, new Gson().toJson(listResponse));

                    if (Util.getProductToCart(mContext) != null) {
                        if (Util.getProductToCart(mContext).contains(categoryItemTemp.getName())) {
                            Util.setProductToCart(mContext, Util.getProductToCart(mContext).replace(categoryItemTemp.getName(), ""));
                        }
                    }
                    notifyDataSetChanged();

                } else {
                    categoryItemTemp.setCount(categoryItemTemp.getCount() - 1);

                    ProductListResponse listResponse = new ProductListResponse();
                    for (ProductListObject productListObject : productListObjects) {

                        if (productListObject.getName().equalsIgnoreCase(categoryItemTemp.getName())) {
                            productListObject.setCount(productListObject.getCount() - 1);

                            listResponse.setList(categoryItem);
                            Util.setCartItem(mContext, new Gson().toJson(listResponse));
                            notifyDataSetChanged();
                        }
                    }
                }
            }

        } else {

            ProductListResponse productListResponse1 = new ProductListResponse();
            ArrayList<ProductListObject> productListObjects = new ArrayList<>();
            categoryItemTemp.setCount(1);
            productListObjects.add(categoryItemTemp);
            productListResponse1.setList(productListObjects);

            Util.setCartItem(mContext, new Gson().toJson(productListResponse1));

            if (Util.getProductToCart(mContext) != null)
                Util.setProductToCart(mContext, Util.getProductToCart(mContext) + "," + categoryItemTemp.getName());
            else
                Util.setProductToCart(mContext, categoryItemTemp.getName());

            notifyDataSetChanged();

            //Toast.makeText(mContext, "Item added to cart!!", Toast.LENGTH_SHORT).show();
        }
    }
}