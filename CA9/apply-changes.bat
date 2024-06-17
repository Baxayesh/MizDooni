kubectl delete -f mysql-pvc.yaml -n mishap
kubectl delete -f mysql-deployment.yaml -n mishap
kubectl delete -f mysql-service.yaml -n mishap
kubectl delete -f backend-deployment.yaml -n mishap
kubectl delete -f backend-service.yaml -n mishap
kubectl delete -f frontend-deployment.yaml -n mishap
kubectl delete -f frontend-service.yaml -n mishap

kubectl apply -f mysql-pvc.yaml -n mishap
kubectl apply -f mysql-deployment.yaml -n mishap
kubectl apply -f mysql-service.yaml -n mishap
kubectl apply -f backend-deployment.yaml -n mishap
kubectl apply -f backend-service.yaml -n mishap
kubectl apply -f frontend-deployment.yaml -n mishap
kubectl apply -f frontend-service.yaml -n mishap