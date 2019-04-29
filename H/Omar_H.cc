#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp>

using namespace std;
using namespace __gnu_pbds;

typedef long long ll;
typedef tree<pair<ll, int>, null_type, less_equal<pair<ll, int>>, rb_tree_tag, tree_order_statistics_node_update> indexed_set;

const int maxn = (int)1e5+2;
const int inf = 10*maxn;

indexed_set st[4*maxn];
ll a[maxn], old;
int t = 1;

void merge(int p){
  for(auto it = st[2*p].begin() ; it != st[2*p].end() ; ++it){
    st[p].insert(*it);
  }
  for(auto it = st[2*p+1].begin() ; it != st[2*p+1].end() ; ++it){
    st[p].insert(*it);
  }
}

void build(int p, int l, int r){
  if(l == r){
    st[p].insert({a[l], t++});
    return;
  }

  int mid = (l+r)/2;
  build(2*p, l, mid);
  build(2*p+1, mid+1, r);

  merge(p);
}

int Count(int p, ll v){
  return st[p].order_of_key({v-1, inf});
}

int query(int p, int l, int r, int i, int j, ll v){
  if(l>j || r<i) return 0;
  if(l>=i && r<=j) return Count(p, v);

  int mid = (l+r)/2;
  return query(2*p, l, mid, i, j, v) + query(2*p+1, mid+1, r, i, j, v);
}

void adjust(int p, ll v){
  st[p].erase(st[p].find_by_order(st[p].order_of_key({old, 0})));
  st[p].insert({v, t});
}

void update(int p, int l, int r, int idx, ll v){
  if(l == r){
    st[p].clear();
    st[p].insert({v, t});
    return;
  }

  int mid = (l+r)/2;
  if(idx <= mid) update(2*p, l, mid, idx, v);
  else update(2*p+1, mid+1, r, idx, v);

  adjust(p, v);
}

int main(){
  int n, q;
  scanf("%d%d", &n, &q);

  for(int i=0 ; i<n ; i++) scanf("%lld", &a[i]);

  build(1, 0, n-1);

  while(q--){
    int type;
    scanf("%d", &type);
    if(type == 1){
      int l, r;
      ll v;
      scanf("%d%d%lld", &l, &r, &v);
      l--, r--;
      printf("%d\n", query(1, 0, n-1, l, r, v));
    }
    else{
      int idx;
      ll v;
      scanf("%d%lld", &idx, &v);
      idx--;
      old = a[idx];
      a[idx] = v;
      update(1, 0, n-1, idx, v);
      t++;
    }
  }

  return 0;
}