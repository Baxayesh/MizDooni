kubectl delete -f frontend-service.yaml -n mishap
kubectl delete -f frontend-deployment.yaml -n mishap
kubectl delete -f backend-service.yaml -n mishap
kubectl delete -f backend-deployment.yaml -n mishap
kubectl delete -f mysql-service.yaml -n mishap
kubectl delete -f mysql-deployment.yaml -n mishap
kubectl delete -f mysql-pvc.yaml -n mishap

kubectl apply -f mysql-pvc.yaml -n mishap
kubectl apply -f mysql-deployment.yaml -n mishap
kubectl apply -f mysql-service.yaml -n mishap
timeout /t 180 /nobreak >nul
kubectl apply -f backend-deployment.yaml -n mishap
kubectl apply -f backend-service.yaml -n mishap
kubectl apply -f frontend-deployment.yaml -n mishap
kubectl apply -f frontend-service.yaml -n mishap


start /B kubectl -n mishap port-forward svc/frontend 80:80 >nul
start /B kubectl -n mishap port-forward svc/backend 443:443 >nul
